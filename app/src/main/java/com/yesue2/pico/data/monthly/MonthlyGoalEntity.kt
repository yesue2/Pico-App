package com.yesue2.pico.data.monthly

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "monthly_goals")
data class MonthlyGoalEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    var category: Int = 0,
    val importance: Int,
    var isCompleted: Boolean = false,
    val goalAmount: Int,
    val unit: String,
    val startDate: Long,
    val endDate: Long,
    val progress: Int = 0, // 진행 상황 (기본값 0)
    val trackingMethod: String
)
