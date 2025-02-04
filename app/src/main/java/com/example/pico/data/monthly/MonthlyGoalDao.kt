package com.example.pico.data.monthly

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MonthlyGoalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: MonthlyGoalEntity)

    @Update
    suspend fun updateGoal(goal: MonthlyGoalEntity)

    @Delete
    suspend fun deleteGoal(goal: MonthlyGoalEntity)

    // id별 데이터 조회 => Detail 화면
    @Query("SELECT * FROM monthly_goals WHERE id = :goalId")
    suspend fun getGoalById(goalId: Int): MonthlyGoalEntity?

    // 모든 데이터 조회 => Home 화면
    @Query("SELECT * FROM monthly_goals ORDER BY endDate ASC")
    fun getAllGoals(): Flow<List<MonthlyGoalEntity>>

    // 진행상황 업데이트
    @Query("UPDATE monthly_goals SET progress = progress + 1 WHERE id = :goalId")
    suspend fun incrementProgress(goalId: Int)

    // id별 데이터 삭제 => Detail 화면
    @Query("delete from monthly_goals where id = :goalId")
    suspend fun deleteGoalTodoById(goalId: Int)
}
