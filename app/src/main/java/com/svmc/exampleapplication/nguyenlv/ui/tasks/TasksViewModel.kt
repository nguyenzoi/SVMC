package com.svmc.exampleapplication.nguyenlv.ui.tasks

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.svmc.exampleapplication.nguyenlv.data.PreferencesManager
import com.svmc.exampleapplication.nguyenlv.data.SortOrder
import com.svmc.exampleapplication.nguyenlv.data.Task
import com.svmc.exampleapplication.nguyenlv.data.TaskDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class TasksViewModel @ViewModelInject constructor(
    private val taskDao: TaskDao,
    private val preferencesManager: PreferencesManager
) : ViewModel() {
    val searchQuery = MutableStateFlow("")

    var sortOrder = MutableStateFlow(SortOrder.BY_DATE)
    var hideCompleted = MutableStateFlow(false)

    fun processPreference() = viewModelScope.launch {
        sortOrder.value = preferencesManager.sortOrder.first()
        hideCompleted.value = preferencesManager.hideCompleted.first()
    }


    private val tasksFlow = combine(
        searchQuery, sortOrder, hideCompleted
    ) { query, sortOrder, hideCompleted ->
        Triple(query, sortOrder, hideCompleted)
    }
        .flatMapLatest {
            taskDao.getTasks(it.first, it.second, it.third)
        }

    fun onSortOrderSeleted(sortOrder: SortOrder) = viewModelScope.launch {
        preferencesManager.updateSortOrder(sortOrder)
    }

    fun onHideCompletedClick(hideCompleted: Boolean) = viewModelScope.launch {
        preferencesManager.updateHideCompleted(hideCompleted)
    }

    fun onTaskSelected(task: Task) {

    }

    fun onCheckedChanged(task: Task, isChecked: Boolean) = viewModelScope.launch {
        taskDao.update(task.copy(completed = isChecked))
    }

    val tasks = tasksFlow.asLiveData()

    init {
        //  processPreference()
    }
}