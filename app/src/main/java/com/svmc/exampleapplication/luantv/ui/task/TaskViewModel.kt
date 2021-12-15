package com.svmc.exampleapplication.luantv.ui.task

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.svmc.exampleapplication.luantv.data.Order
import com.svmc.exampleapplication.luantv.data.PreferenceManager
import com.svmc.exampleapplication.luantv.data.Task
import com.svmc.exampleapplication.luantv.data.TaskDao
import com.svmc.exampleapplication.luantv.ui.ADD_TASK_RESULT_OK
import com.svmc.exampleapplication.luantv.ui.EDIT_TASK_RESULT_OK
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TaskViewModel @ViewModelInject constructor(
    private val taskDao:TaskDao,
    private val preferenceManager: PreferenceManager,
    @Assisted private val state: SavedStateHandle
): ViewModel() {

    val searchQuery = state.getLiveData("searchQuery", "")

    private val tasksEventChanel = Channel<TasksEvent>()
    val tasksEvent = tasksEventChanel.receiveAsFlow()

    val preferenceFlow = preferenceManager.preferenceFlow

    private val tasksFlow = combine(searchQuery.asFlow(), preferenceFlow) {
        searchQuery, filter -> Pair(searchQuery, filter)
    }.flatMapLatest { (searchQuery, filter) ->
        taskDao.getTask(searchQuery, filter.sortOrder, filter.hideCompleted)
    }

    val tasks = tasksFlow.asLiveData()

    fun updateSort(order: Order) = viewModelScope.launch {
        preferenceManager.updateSortOder(order)
    }

    fun updateHideCompleted (hide: Boolean) = viewModelScope.launch {
        preferenceManager.updateHideCompleted(hide)
    }

    fun updateCompletedTask(task: Task, completed: Boolean) = viewModelScope.launch {
        taskDao.update(task.copy(completed = completed))
    }

    fun onTaskSwiped(task: Task) = viewModelScope.launch {
        taskDao.delete(task)
        tasksEventChanel.send(TasksEvent.ShowUndoDeleteTaskMessage(task))
    }

    fun onUndoDeleteSwiped(task: Task) = viewModelScope.launch {
        taskDao.insert(task)
    }

    fun onItemSelected(task: Task) = viewModelScope.launch {
        tasksEventChanel.send(TasksEvent.NavigateToEditScreen(task))
    }

    fun onClickAddButton () = viewModelScope.launch {
        tasksEventChanel.send(TasksEvent.NavigateToAddScreen)
    }

    fun onAddEditResult(result: Int) = viewModelScope.launch {
        when (result) {
            ADD_TASK_RESULT_OK -> tasksEventChanel.send(TasksEvent.ShowAddEditResult("created new task"))
            EDIT_TASK_RESULT_OK -> tasksEventChanel.send(TasksEvent.ShowAddEditResult(" updated task"))
        }
    }

    fun deleteAllCompletedTask() = viewModelScope.launch {
        tasksEventChanel.send(TasksEvent.NavigateToDeleteAllCompletedScreen)
    }

    sealed class TasksEvent {
        object NavigateToAddScreen: TasksEvent()
        data class NavigateToEditScreen(val task: Task): TasksEvent()
        data class ShowUndoDeleteTaskMessage (val task: Task): TasksEvent()
        data class ShowAddEditResult (val text: String): TasksEvent()
        object NavigateToDeleteAllCompletedScreen: TasksEvent()
    }
}