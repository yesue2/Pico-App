package com.example.pico.repository

import com.example.pico.data.daily.DailyTodoEntity
import com.example.pico.data.daily.DailyTodoDao
import kotlinx.coroutines.flow.Flow

class DailyTodoRepository(private val dailyTodoDao: DailyTodoDao) {
    suspend fun insertDaily(daily: DailyTodoEntity) {
        dailyTodoDao.insert(daily)
    }

    fun getAllTodos(): Flow<List<DailyTodoEntity>> {
        return dailyTodoDao.getAllTodos()
    }

    suspend fun update(daily: DailyTodoEntity) {
        dailyTodoDao.update(daily)
    }

    suspend fun deleteAllTodos(daily: DailyTodoEntity) {
        dailyTodoDao.deleteAllTodos(daily)
    }

    fun getCompletedDailyTodos(): Flow<List<DailyTodoEntity>> {
        return dailyTodoDao.getCompletedTodos()
    }

    suspend fun deleteAllTodos() {
        dailyTodoDao.deleteAllTodos()
    }

    suspend fun getTodoById(todoId: Int): DailyTodoEntity? {
        return dailyTodoDao.getTodoById(todoId)
    }
}
