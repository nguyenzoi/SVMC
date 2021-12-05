package com.svmc.exampleapplication.nguyenlv.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("Select * from table_task where name like '%' || :searchQuery || '%' order by important DESC")
    fun getTasks(searchQuery: String): Flow<List<Task>>
}