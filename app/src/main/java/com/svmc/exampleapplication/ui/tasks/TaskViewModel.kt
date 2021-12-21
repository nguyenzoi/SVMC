package com.svmc.exampleapplication.ui.tasks

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.svmc.exampleapplication.data.Order
import com.svmc.exampleapplication.data.PreferenceManager
import com.svmc.exampleapplication.data.Task
import com.svmc.exampleapplication.data.TaskDao
import com.svmc.exampleapplication.ui.TaskStatus.ADD_TASK_RESULT_OK
import com.svmc.exampleapplication.ui.TaskStatus.UPDATE_TASK_RESULT_OK
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TaskViewModel @ViewModelInject constructor(
    private val taskDao: TaskDao,
    private val preferenceManager: PreferenceManager,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    val taskEventChanel = Channel<TaskEvent>()
    val taskEvent = taskEventChanel.receiveAsFlow()

    val searchText = state.getLiveData("search_text","")
    val dataStoreFlow = preferenceManager.dataStoreFlow

    val taskFlow = combine(searchText.asFlow(), dataStoreFlow) {
        searchText, filterPreference ->
        Pair(searchText, filterPreference)
    }.flatMapLatest { (searchText, filterPreference) ->
        taskDao.getTasks(searchText, filterPreference.order, filterPreference.hideCompleted)
    }

    val tasks = taskFlow.asLiveData()

    fun onSortUpdate (order: Order) = viewModelScope.launch {
        preferenceManager.updateSortBy(order)
    }

    fun onHideCompletedUpdated (status: Boolean) = viewModelScope.launch {
        preferenceManager.updateHideCompleted(status)
    }

    fun onFloatAddClick() = viewModelScope.launch {
        taskEventChanel.send(TaskEvent.NavigateAddScreen)
    }

    fun onClickItem(task: Task) = viewModelScope.launch {
        taskEventChanel.send(TaskEvent.NavigateAddEditScreen(task))
    }

    fun onClickCheckBoxCompleted (task: Task, isChecked: Boolean) = viewModelScope.launch {
        taskDao.update(task.copy(completed = isChecked))
        taskEventChanel.send(TaskEvent.OnUpdateHideCompletedTask(task, isChecked))
    }

    fun onSwipeToDeleteItem (task: Task) = viewModelScope.launch {
        taskDao.delete(task)
        taskEventChanel.send(TaskEvent.OnUndoTask(task))
    }

    fun undoTask (task: Task) = viewModelScope.launch {
        taskDao.insert(task)
    }

    fun onAddUpdateResult(result: Int) = viewModelScope.launch {
        when(result) {
            ADD_TASK_RESULT_OK -> {
                taskEventChanel.send(TaskEvent.NavigateSaveBack("Added Task"))
            }
            UPDATE_TASK_RESULT_OK -> {
                taskEventChanel.send(TaskEvent.NavigateSaveBack("Added Task"))
            }
        }
    }

    fun onDeleteAllCompletedTask() = viewModelScope.launch {
        taskEventChanel.send(TaskEvent.NavigateToDeleteAllCompletedTaskScreen)
    }

    sealed class TaskEvent {
        object NavigateAddScreen: TaskEvent()
        object NavigateToDeleteAllCompletedTaskScreen: TaskEvent()

        data class OnUpdateHideCompletedTask(val task: Task, val isChecked: Boolean): TaskEvent()
        data class NavigateAddEditScreen(val task: Task): TaskEvent()
        data class OnUndoTask (val task: Task): TaskEvent()

        data class NavigateSaveBack(val msg: String): TaskEvent()
    }
}