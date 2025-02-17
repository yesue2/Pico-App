package com.example.pico.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pico.data.monthly.MonthlyGoalEntity
import com.example.pico.repository.MonthlyGoalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MonthlyGoalViewModel @Inject constructor(
    private val repository: MonthlyGoalRepository
) : ViewModel() {
    // StateFlow로 현재 선택된 goal 데이터를 관리
    private val _selectedGoal = MutableStateFlow<MonthlyGoalEntity?>(null)
    val selectedGoal: StateFlow<MonthlyGoalEntity?> get() = _selectedGoal

    fun insertGoal(goal: MonthlyGoalEntity) {
        viewModelScope.launch {
            repository.insertGoal(goal)
        }
    }

    fun updateGoal(goal: MonthlyGoalEntity) {
        viewModelScope.launch {
            repository.updateGoal(goal)
        }
    }

    fun deleteGoal(goal: MonthlyGoalEntity) {
        viewModelScope.launch {
            repository.deleteGoal(goal)
        }
    }

    fun getAllGoals(): Flow<List<MonthlyGoalEntity>> {
        return repository.getAllGoals()
    }

    // goal ID로 데이터 가져오기
    fun loadGoalById(goalId: Int) {
        viewModelScope.launch {
            val goal = repository.getGoalById(goalId)
            _selectedGoal.value = goal
        }
    }

    // 특정 ID로 goal 삭제
    fun deleteGoalById(goalId: Int) {
        viewModelScope.launch {
            repository.deleteGoalById(goalId)
        }
    }

    // 진행 방식에 따라 자동으로 진행도를 증가시키는 함수
    fun updateProgressAutomatically(goal: MonthlyGoalEntity) {
        viewModelScope.launch {
            val currentTime = System.currentTimeMillis()

            // 목표 시작일부터 오늘까지 지난 일수 계산
            val daysPassed = ((currentTime - goal.startDate) / (1000 * 60 * 60 * 24)).toInt() + 1

            // 진행 방식에 따라 진행도를 업데이트
            val updatedProgress = when (goal.trackingMethod) {
                "매일 1 자동 증가" -> {
                    // 시작일 이후 진행된 일수 만큼 진행도가 있어야 함
                    val expectedProgress = daysPassed.coerceAtLeast(0) // 최소 0 이상
                    expectedProgress.coerceAtMost(goal.goalAmount) // 목표량 초과 방지
                }
                "목표에 맞춰 자동 계산" -> calculateProgress(goal)
                else -> goal.progress
            }

            // 목표량을 초과하면 completeGoal 호출
            if (updatedProgress >= goal.goalAmount) {
                completeGoal(goal, true)
            } else if (updatedProgress != goal.progress) {
                val updatedGoal = goal.copy(progress = updatedProgress)
                repository.updateGoal(updatedGoal)
                _selectedGoal.value = updatedGoal
            }
        }
    }


    // 목표 완료 시 진행도를 목표량으로 변경
    fun completeGoal(goal: MonthlyGoalEntity, isCompleted: Boolean) {
        val updatedGoal = goal.copy(
            isCompleted = isCompleted,
            progress = if (isCompleted) goal.goalAmount else goal.progress
        )
        viewModelScope.launch {
            repository.updateGoal(updatedGoal)
            _selectedGoal.value = updatedGoal
        }
    }


    // 사용자가 직접 클릭하여 진행도를 업데이트
    fun updateProgressManually(goal: MonthlyGoalEntity, newProgress: Int) {
        val updatedGoal = goal.copy(progress = newProgress)
        viewModelScope.launch {
            if (newProgress >= goal.goalAmount) {
                completeGoal(goal, true)
            } else {
                repository.updateGoal(updatedGoal)
                _selectedGoal.value = updatedGoal // UI 자동 업데이트
            }
        }
    }

    // 자동 계산 로직
    private fun calculateProgress(goal: MonthlyGoalEntity): Int {
        val currentTime = System.currentTimeMillis()

        // 목표 기간 (일 단위)
        val totalDays = ((goal.endDate - goal.startDate) / (1000 * 60 * 60 * 24)).toInt()

        // 오늘까지 경과한 일수 (현재 시간 - 시작 날짜)
        val daysPassed = ((currentTime - goal.startDate) / (1000 * 60 * 60 * 24)).toInt()

        return when {
            totalDays <= 0 -> goal.goalAmount // 목표 기간이 0일이면 목표량 그대로 반환
            daysPassed < 0 -> 0 // 아직 목표 시작 전이면 진행도 0
            daysPassed >= totalDays -> goal.goalAmount // 목표 종료일이 지나면 목표량 반환
            else -> ((goal.goalAmount.toDouble() / (totalDays + 1)) * (daysPassed + 1)).toInt() // 마지막 날까지 균등 배분
        }
    }

}
