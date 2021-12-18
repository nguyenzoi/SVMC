package com.svmc.exampleapplication.ui.tasks

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.svmc.exampleapplication.data.TaskDao

class TaskViewModel @ViewModelInject constructor(
    private val taskDao: TaskDao): ViewModel() {


}