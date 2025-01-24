package com.example.pico

import android.app.Application
import com.example.pico.data.daily.DailyTodoDatabase
import com.example.pico.repository.DailyTodoRepository

class TodoApp : Application() {
    val database by lazy { DailyTodoDatabase.getDailyDatabase(this) }
    val repository by lazy { DailyTodoRepository(database.dailyTodoDao()) }
}
