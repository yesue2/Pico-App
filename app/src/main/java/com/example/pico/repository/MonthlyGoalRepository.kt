package com.example.pico.repository

import com.example.pico.data.monthly.MonthlyGoalDao
import com.example.pico.data.monthly.MonthlyGoalEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MonthlyGoalRepository @Inject constructor(
    private val monthlyGoalDao: MonthlyGoalDao
) {

    suspend fun insertGoal(goal: MonthlyGoalEntity) {
        monthlyGoalDao.insertGoal(goal)
    }

    suspend fun updateGoal(goal: MonthlyGoalEntity) {
        monthlyGoalDao.updateGoal(goal)
    }

    suspend fun deleteGoal(goal: MonthlyGoalEntity) {
        monthlyGoalDao.deleteGoal(goal)
    }

    suspend fun getGoalById(goalId: Int): MonthlyGoalEntity? {
        return monthlyGoalDao.getGoalById(goalId)
    }

    suspend fun deleteDailyTodoById(todoId: Int) {
        return monthlyGoalDao.deleteGoalTodoById(todoId)
    }

    fun getAllGoals(): Flow<List<MonthlyGoalEntity>> {
        return monthlyGoalDao.getAllGoals()
    }

    suspend fun incrementProgress(goalId: Int) {
        monthlyGoalDao.incrementProgress(goalId)
    }
}
