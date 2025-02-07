package com.example.pico

import android.app.Application
import com.example.pico.data.daily.DailyTodoDatabase
import com.example.pico.data.monthly.MonthlyGoalDatabase
import com.example.pico.repository.DailyTodoRepository
import com.example.pico.repository.MonthlyGoalRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TodoApp : Application()
