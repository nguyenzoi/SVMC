package com.svmc.exampleapplication.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("Select * from task_table where name like '%' || :searchQuery || '%' order by importance desc")
    fun getTasks(searchQuery: String?): Flow<List<Task>>

    @Delete
    suspend fun delete(task: Task)

    @Insert
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)
}