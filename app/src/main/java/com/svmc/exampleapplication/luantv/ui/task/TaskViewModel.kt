package com.svmc.exampleapplication.luantv.ui.task

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.svmc.exampleapplication.luantv.data.TaskDao

class TaskViewModel @ViewModelInject constructor(
    private val taskDao:TaskDao
): ViewModel() {

    val tasks = taskDao.getTask().asLiveData()
}