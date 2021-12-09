package com.svmc.exampleapplication.luantv.data

import androidx.room.*

@Dao
interface TaskDao {

    @Query("Select * from task_table")
    suspend fun query(): List<Task>;

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Update
    suspend fun update(task: Task)
}