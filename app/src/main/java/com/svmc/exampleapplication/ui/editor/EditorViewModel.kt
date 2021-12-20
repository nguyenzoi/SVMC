package com.svmc.exampleapplication.ui.editor

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.svmc.exampleapplication.data.PreferenceManager
import com.svmc.exampleapplication.data.Task
import com.svmc.exampleapplication.data.TaskDao

class EditorViewModel @ViewModelInject constructor(
    private val taskDao: TaskDao,
    private val preferenceManager: PreferenceManager,
    @Assisted private val state: SavedStateHandle
): ViewModel() {

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
}