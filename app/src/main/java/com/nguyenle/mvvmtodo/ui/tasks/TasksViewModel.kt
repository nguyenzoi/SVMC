package com.nguyenle.mvvmtodo.ui.tasks

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nguyenle.mvvmtodo.data.PreferencesManager
import com.nguyenle.mvvmtodo.data.SortOrder
import com.nguyenle.mvvmtodo.data.TaskDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class TasksViewModel @ViewModelInject constructor(
    private val taskDao: TaskDao,
    private val preferencesManager: PreferencesManager
) : ViewModel() {
    val searchQuery = MutableStateFlow("")
    val sortOrder = preferencesManager.sortOrder
    val hideCompleted = preferencesManager.hideCompleted
    private val taskFlow =
        combine(searchQuery, sortOrder, hideCompleted) { query, sortOrder, hideCompleted ->
            Triple(query, sortOrder, hideCompleted)
        }
            .flatMapLatest {
                taskDao.getTasks(it.first, it.second, it.third)
            }

    fun onSortOrderSeleted(sortOrder: SortOrder) = viewModelScope.launch {
        preferencesManager.updateSortOrder(sortOrder)
    }

    fun onHideCompletedChanged(hideCompleted: Boolean) = viewModelScope.launch {
        preferencesManager.updateHideCompleted(hideCompleted)
    }

    val task = taskFlow.asLiveData()

}