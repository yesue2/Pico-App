package com.example.pico.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.pico.data.daily.DailyTodoDao
import com.example.pico.data.daily.DailyTodoDatabase
import com.example.pico.data.monthly.MonthlyGoalDao
import com.example.pico.data.monthly.MonthlyGoalDatabase
import com.example.pico.repository.DailyTodoRepository
import com.example.pico.repository.MonthlyGoalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // DailyTodo Database
    @Provides
    @Singleton
    fun provideDailyDatabase(@ApplicationContext context: Context): DailyTodoDatabase {
        return Room.databaseBuilder(
            context,
            DailyTodoDatabase::class.java,
            "daily_table"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDailyDao(database: DailyTodoDatabase): DailyTodoDao {
        return database.dailyTodoDao()
    }

    @Provides
    @Singleton
    fun provideDailyRepository(dailyDao: DailyTodoDao): DailyTodoRepository {
        return DailyTodoRepository(dailyDao)
    }

    // MonthlyGoal Database
    @Provides
    @Singleton
    fun provideMonthlyDatabase(@ApplicationContext context: Context): MonthlyGoalDatabase {
        return Room.databaseBuilder(
            context,
            MonthlyGoalDatabase::class.java,
            "monthly_goals"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMonthlyDao(database: MonthlyGoalDatabase): MonthlyGoalDao {
        return database.monthlyGoalDao()
    }

    @Provides
    @Singleton
    fun provideMonthlyRepository(monthlyDao: MonthlyGoalDao): MonthlyGoalRepository {
        return MonthlyGoalRepository(monthlyDao)
    }

    // SharedPreferences
    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("PicoPreferences", Context.MODE_PRIVATE)
    }

}