package com.example.pico.repository

import com.example.pico.data.daily.DailyTodoEntity
import com.example.pico.data.daily.DailyTodoDao
import kotlinx.coroutines.flow.Flow

class DailyTodoRepository(private val dailyTodoDao: DailyTodoDao) {
    suspend fun insertDaily(daily: DailyTodoEntity) {
        dailyTodoDao.insert(daily)
    }

    suspend fun update(daily: DailyTodoEntity) {
        dailyTodoDao.update(daily)
    }

    suspend fun deleteAllTodos(daily: DailyTodoEntity) {
        dailyTodoDao.deleteAllTodos(daily)
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
}
