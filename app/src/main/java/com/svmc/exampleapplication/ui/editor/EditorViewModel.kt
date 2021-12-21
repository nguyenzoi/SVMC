package com.svmc.exampleapplication.ui.editor

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.svmc.exampleapplication.data.PreferenceManager
import com.svmc.exampleapplication.data.Task
import com.svmc.exampleapplication.data.TaskDao
import com.svmc.exampleapplication.ui.TaskStatus
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class EditorViewModel @ViewModelInject constructor(
    private val taskDao: TaskDao,
    private val preferenceManager: PreferenceManager,
    @Assisted private val state: SavedStateHandle
): ViewModel() {

    val editorEventChannel = Channel<AddEditEvent> ()
    val editorEventFlow = editorEventChannel.receiveAsFlow()

    val task = state.get<Task>("task")

    var taskName = state.get<String>("taskName")?:task?.name?:""
        set(value) {
            field = value
            state.set("taskName", value)
        }
    var taskImportance = state.get<Boolean>("taskImportance")?:task?.importance?:false
        set(value) {
            field = value
            state.set("taskImportance", value)
        }

    fun onSaveButton() = viewModelScope.launch {
        if (taskName.isBlank()) {
            editorEventChannel.send(AddEditEvent.Fail("Wrong Name"))
        } else {
            if (task != null) {
                taskDao.update(task.copy(name = taskName, importance = taskImportance))
                editorEventChannel.send(AddEditEvent.NavigateBackWithResult(TaskStatus.UPDATE_TASK_RESULT_OK, "Updated task"))
            } else {
                taskDao.insert(Task(name = taskName, importance = taskImportance))
                editorEventChannel.send(AddEditEvent.NavigateBackWithResult(TaskStatus.ADD_TASK_RESULT_OK, "Added task"))
            }
        }
    }

    sealed class AddEditEvent {

        data class NavigateBackWithResult(val result: Int, val msg: String): AddEditEvent()
        data class Fail(val msg: String): AddEditEvent()
    }
}