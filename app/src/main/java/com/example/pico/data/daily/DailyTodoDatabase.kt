package com.example.pico.data.daily

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DailyTodoEntity::class], version = 2, exportSchema = false)
abstract class DailyTodoDatabase : RoomDatabase() {
    abstract fun dailyTodoDao(): DailyTodoDao

    companion object {
        @Volatile
        private var INSTANCE: DailyTodoDatabase? = null

        fun getDailyDatabase(context: Context): DailyTodoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, DailyTodoDatabase::class.java, "todo_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
