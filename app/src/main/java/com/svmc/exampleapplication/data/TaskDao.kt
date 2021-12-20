package com.svmc.exampleapplication.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("Select * from task_table")
    fun getTasks(): Flow<List<Task>>

    @Delete
    suspend fun delete(task: Task)

    @Insert
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)
}