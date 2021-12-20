package com.svmc.exampleapplication.ui.tasks

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.svmc.exampleapplication.data.TaskDao

class TaskViewModel @ViewModelInject constructor(
    private val taskDao: TaskDao): ViewModel() {

        val task = taskDao.getTasks().asLiveData()
}