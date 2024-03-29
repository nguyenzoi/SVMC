package com.svmc.exampleapplication.nguyenlv.data

import androidx.room.*
import com.svmc.exampleapplication.nguyenlv.ui.tasks.SortOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    fun getTasks(query: String, sortOrder: SortOrder, hideCompleted: Boolean): Flow<List<Task>> =
        when (sortOrder) {
            SortOrder.BY_DATE -> getTasksSortedByDate(query, hideCompleted)
            SortOrder.BY_NAME -> getTasksSortedByName(query, hideCompleted)
        }

    @Query("Select * from table_task where (completed != :hideCompleted or completed = 0)  And name like '%' || :searchQuery || '%' order by important DESC, name")
    fun getTasksSortedByName(searchQuery: String, hideCompleted: Boolean): Flow<List<Task>>

    @Query("Select * from table_task where (completed != :hideCompleted or completed = 0)  And name like '%' || :searchQuery || '%' order by important DESC, created" )
    fun getTasksSortedByDate(searchQuery: String, hideCompleted: Boolean): Flow<List<Task>>
}