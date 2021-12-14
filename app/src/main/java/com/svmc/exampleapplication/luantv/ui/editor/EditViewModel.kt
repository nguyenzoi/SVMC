package com.svmc.exampleapplication.luantv.ui.editor

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.svmc.exampleapplication.luantv.data.Task
import com.svmc.exampleapplication.luantv.data.TaskDao

class EditViewModel @ViewModelInject constructor(
    val taskDao: TaskDao,
    @Assisted val state: SavedStateHandle
): ViewModel(){
    val task = state.get<Task>("task")
    var taskName = state.get<String>("taskName")?:task?.name?:""
        set(value) {
            field = value
        }
    var taskImportance = state.get<Boolean>("taskImportance")?:task?.important?:false
        set(value) {
            field = value
        }
}