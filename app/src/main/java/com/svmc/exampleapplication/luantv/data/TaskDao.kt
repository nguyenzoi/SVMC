package com.svmc.exampleapplication.luantv.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    fun getTask(searchQuery: String,  orderBy: Order, hideCompleted: Boolean): Flow<List<Task>>
    =  when(orderBy) {
        Order.BY_DATE-> getTaskByOrderDate(searchQuery, hideCompleted)
        Order.BY_NAME-> getTaskByOrderName(searchQuery, hideCompleted)
    }

    @Query("Select * from task_table where (completed != :hideCompleted or completed = 0) and name like '%' || :searchQuery || '%' order by name asc ")
    fun getTaskByOrderName(searchQuery: String, hideCompleted: Boolean): Flow<List<Task>>

    @Query("Select * from task_table where (completed != :hideCompleted or completed = 0) and name like '%' || :searchQuery || '%' order by created asc ")
    fun getTaskByOrderDate(searchQuery: String, hideCompleted: Boolean): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Update
    suspend fun update(task: Task)

    @Query("Delete from task_table where completed")
    suspend fun deleteAllCompleted()
}