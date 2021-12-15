package com.svmc.exampleapplication.luantv.ui.editor

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.svmc.exampleapplication.luantv.data.Task
import com.svmc.exampleapplication.luantv.data.TaskDao
import com.svmc.exampleapplication.luantv.ui.ADD_TASK_RESULT_OK
import com.svmc.exampleapplication.luantv.ui.EDIT_TASK_RESULT_OK
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class EditViewModel @ViewModelInject constructor(
    val taskDao: TaskDao,
    @Assisted private val state: SavedStateHandle
): ViewModel(){
    private val editTaskEventChannel = Channel<AddEditTaskEvent>()
    val editTaskEvent = editTaskEventChannel.receiveAsFlow()

    val task = state.get<Task>("task")
    var taskName = state.get<String>("taskName")?:task?.name?:""
        set(value) {
            field = value
        }
    var taskImportance = state.get<Boolean>("taskImportance")?:task?.important?:false
        set(value) {
            field = value
        }

    fun onSaveClicked () = viewModelScope.launch {
        if (taskName.isBlank()) {
            editTaskEventChannel.send(AddEditTaskEvent.NotifyInvalidValue("Name is wrong. Please type again"))
        } else {
            if (task != null) {
                updateTask(task.copy(name = taskName, important = taskImportance))
            } else {
                createTask(Task(taskName, taskImportance))
            }
        }
    }

    private fun createTask(task: Task) = viewModelScope.launch {
        taskDao.insert(task)
        editTaskEventChannel.send(AddEditTaskEvent.NavigateBackWithResult(ADD_TASK_RESULT_OK))
    }
    private fun updateTask(task: Task) = viewModelScope.launch {
        taskDao.update(task)
        editTaskEventChannel.send(AddEditTaskEvent.NavigateBackWithResult(EDIT_TASK_RESULT_OK))
    }

    sealed class AddEditTaskEvent {

        data class NotifyInvalidValue(val text: String): AddEditTaskEvent()
        data class NavigateBackWithResult(val result: Int): AddEditTaskEvent()
    }
}