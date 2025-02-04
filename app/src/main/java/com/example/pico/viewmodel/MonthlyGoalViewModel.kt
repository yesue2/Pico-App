package com.example.pico.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.pico.data.monthly.MonthlyGoalEntity
import com.example.pico.repository.MonthlyGoalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MonthlyGoalViewModel(private val repository: MonthlyGoalRepository) : ViewModel() {
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

    fun incrementProgress(goalId: Int) {
        viewModelScope.launch {
            repository.incrementProgress(goalId)
        }
    }

    // goal ID로 데이터 가져오기
    fun loadDailyTodoById(goalId: Int) {
        viewModelScope.launch {
            val goal = repository.getGoalById(goalId)
            _selectedGoal.value = goal
        }
    }

    // 특정 ID로 goal 삭제
    fun deleteDailyTodoById(goalId: Int) {
        viewModelScope.launch {
            repository.deleteDailyTodoById(goalId)
        }
    }
}

class MonthlyGoalViewModelFactory(private val repository: MonthlyGoalRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MonthlyGoalViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MonthlyGoalViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}