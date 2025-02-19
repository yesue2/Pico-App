package com.yesue2.pico.data.monthly

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MonthlyGoalEntity::class], version = 1, exportSchema = false)
abstract class MonthlyGoalDatabase : RoomDatabase() {
    abstract fun monthlyGoalDao(): MonthlyGoalDao

    companion object {
        @Volatile
        private var INSTANCE: MonthlyGoalDatabase? = null

        fun getMonthlyDatabase(context: Context): MonthlyGoalDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MonthlyGoalDatabase::class.java,
                    "monthly_goal_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
