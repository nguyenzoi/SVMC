package com.svmc.exampleapplication.luantv.ui.task

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.svmc.exampleapplication.luantv.data.TaskDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class TaskViewModel @ViewModelInject constructor(
    private val taskDao:TaskDao
): ViewModel() {

    val searchQuery = MutableStateFlow("")
    private val tasksFlow = searchQuery.flatMapLatest {
        taskDao.getTask(it)
    }

    val tasks = tasksFlow.asLiveData()

}