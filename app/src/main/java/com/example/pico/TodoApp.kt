package com.example.pico

import android.app.Application
import com.example.pico.data.daily.DailyTodoDatabase
import com.example.pico.data.monthly.MonthlyGoalDatabase
import com.example.pico.repository.DailyTodoRepository
import com.example.pico.repository.MonthlyGoalRepository

class TodoApp : Application() {
    val dailyDatabase by lazy { DailyTodoDatabase.getDailyDatabase(this) }
    val monthlyDatabase by lazy { MonthlyGoalDatabase.getMonthlyDatabase(this) }

    val dailyRepository by lazy { DailyTodoRepository(dailyDatabase.dailyTodoDao()) }
    val monthlyRepository by lazy { MonthlyGoalRepository(monthlyDatabase.monthlyGoalDao()) }

}
