package com.example.pico.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.pico.data.monthly.MonthlyGoalEntity
import com.example.pico.repository.DailyTodoRepository
import com.example.pico.repository.MonthlyGoalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MonthlyGoalViewModel(private val repository: MonthlyGoalRepository) : ViewModel() {

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