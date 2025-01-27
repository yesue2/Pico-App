package com.example.pico.data.daily

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyTodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(daily: DailyTodoEntity)

    @Update
    suspend fun update(daily: DailyTodoEntity)

    @Delete
    suspend fun deleteAllTodos(daily: DailyTodoEntity)

    // 미완료 task => schedule 화면
    @Query("select * from daily_table where isCompleted = 0 order by dueDate asc, importance asc")
    fun getAllDailyTodos(): Flow<List<DailyTodoEntity>>

    // 완료 task => My 화면
    @Query("select * from daily_table where isCompleted = 1 order by importance asc")
    fun getCompletedDailyTodos(): Flow<List<DailyTodoEntity>>

    // 모든 데이터 삭제
    @Query("DELETE FROM daily_table")
    suspend fun deleteAllTodos()

    // id별 데이터 삭제 => Detail 화면
    @Query("DELETE FROM daily_table WHERE id = :todoId")
    suspend fun deleteDailyTodoById(todoId: Int)

    // id별 데이터 조회 => Detail 화면
    @Query("SELECT * FROM daily_table WHERE id = :todoId")
    suspend fun getDailyTodoById(todoId: Int): DailyTodoEntity?

}
