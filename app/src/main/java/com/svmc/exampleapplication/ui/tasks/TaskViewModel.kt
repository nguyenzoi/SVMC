package com.svmc.exampleapplication.ui.tasks

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import com.svmc.exampleapplication.data.TaskDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class TaskViewModel @ViewModelInject constructor(
    private val taskDao: TaskDao
) : ViewModel() {

    val searchText = MutableStateFlow("")

    val taskFlow = searchText.flatMapLatest { searchText ->
        taskDao.getTasks(searchText)
    }
    val tasks = taskFlow.asLiveData()
}