package com.example.pico.repository

import com.example.pico.data.daily.DailyTodoEntity
import com.example.pico.data.daily.DailyTodoDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DailyTodoRepository @Inject constructor(
    private val dailyTodoDao: DailyTodoDao
) {
    suspend fun insertDaily(daily: DailyTodoEntity) {
        dailyTodoDao.insertDaily(daily)
    }

    suspend fun updateDaily(daily: DailyTodoEntity) {
        dailyTodoDao.updateDaily(daily)
    }

    fun getAllDailyTodos(): Flow<List<DailyTodoEntity>> {
        return dailyTodoDao.getAllDailyTodos()
    }

    fun getCompletedDailyTodos(): Flow<List<DailyTodoEntity>> {
        return dailyTodoDao.getCompletedDailyTodos()
    }

    suspend fun deleteAllTodos() {
        dailyTodoDao.deleteAllTodos()
    }

    suspend fun deleteDailyTodoById(todoId: Int) {
        return dailyTodoDao.deleteDailyTodoById(todoId)
    }

    suspend fun getDailyTodoById(todoId: Int): DailyTodoEntity? {
        return dailyTodoDao.getDailyTodoById(todoId)
    }

    fun getTodayTodos(startOfDay: Long, endOfDay: Long): Flow<List<DailyTodoEntity>> {
        return dailyTodoDao.getTodayTodos(startOfDay, endOfDay)
    }
}
