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
    fun getAllTodos(): Flow<List<DailyTodoEntity>>
/*    @Query("select * from daily_table where isCompleted = 0 order by id asc")
    fun getAllTodos(): Flow<List<DailyTodoEntity>>*/

    // 완료 task => My 화면
    @Query("select * from daily_table where isCompleted = 1 order by importance asc")
    fun getCompletedTodos(): Flow<List<DailyTodoEntity>>

    @Query("DELETE FROM daily_table")
    suspend fun deleteAllTodos() // 모든 데이터 삭제

    @Query("SELECT * FROM daily_table WHERE id = :todoId")
    suspend fun getTodoById(todoId: Int): DailyTodoEntity?

}
