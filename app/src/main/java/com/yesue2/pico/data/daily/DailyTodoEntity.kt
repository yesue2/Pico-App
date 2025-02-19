package com.yesue2.pico.data.daily

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_table")
data class DailyTodoEntity (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var title: String = "",
    var description: String = "",
    var isCompleted: Boolean = false,
    var category: Int = 0,
    var dueDate: Long? = null,
    var importance: Int = 0
)