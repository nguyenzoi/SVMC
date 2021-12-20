package com.svmc.exampleapplication.data

import androidx.room.*
import com.svmc.exampleapplication.ui.tasks.SortBy
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    fun getTasks(searchQuery: String?, sortBy: SortBy, hideCompleted: Boolean): Flow<List<Task>> =
        when (sortBy) {
            SortBy.SORT_BY_NAME -> {
                getTasksSortByName(searchQuery, hideCompleted)
            }
            SortBy.SORT_BY_DATE -> {
                getTasksSortByDate(searchQuery, hideCompleted)
            }
        }

    @Query("Select * from task_table where (completed != :hideCompleted or completed = 0 ) and name like '%' || :searchQuery || '%' order by importance desc, name")
    fun getTasksSortByName(searchQuery: String?, hideCompleted: Boolean ): Flow<List<Task>>

    @Query("Select * from task_table where (completed != :hideCompleted or completed = 0 ) and name like '%' || :searchQuery || '%' order by importance desc, createdDate")
    fun getTasksSortByDate(searchQuery: String?, hideCompleted: Boolean ): Flow<List<Task>>

    @Delete
    suspend fun delete(task: Task)

    @Insert
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)
}