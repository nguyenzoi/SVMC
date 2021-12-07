package com.svmc.exampleapplication.nguyenlv.ui.tasks

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.svmc.exampleapplication.nguyenlv.data.PreferencesManager
import com.svmc.exampleapplication.nguyenlv.data.SortOrder
import com.svmc.exampleapplication.nguyenlv.data.Task
import com.svmc.exampleapplication.nguyenlv.data.TaskDao
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TasksViewModel @ViewModelInject constructor(
    private val taskDao: TaskDao,
    private val preferencesManager: PreferencesManager
) : ViewModel() {
    val searchQuery = MutableStateFlow("")

    var sortOrder = MutableStateFlow(SortOrder.BY_DATE)
    var hideCompleted = MutableStateFlow(false)

    private val tasksEventChannel = Channel<TasksEvent>()
    val tasksEvent = tasksEventChannel.receiveAsFlow()

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

    fun onTaskSwipe(task: Task) = viewModelScope.launch {
        taskDao.delete(task)
        tasksEventChannel.send(TasksEvent.ShowUndoDeleteTaskMessage(task))
    }

    fun onUndoDeleteClick(task: Task) = viewModelScope.launch {
        taskDao.insert(task)
    }

    val tasks = tasksFlow.asLiveData()

    init {
        //  processPreference()
    }

    sealed class TasksEvent {
        data class ShowUndoDeleteTaskMessage(val task: Task) : TasksEvent()
    }
}