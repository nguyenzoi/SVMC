package com.svmc.exampleapplication.ui.tasks

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import com.svmc.exampleapplication.data.TaskDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest

class TaskViewModel @ViewModelInject constructor(
    private val taskDao: TaskDao
) : ViewModel() {

    val searchText = MutableStateFlow("")
    val sortBy = MutableStateFlow(SortBy.SORT_BY_NAME)
    val hideCompleted = MutableStateFlow(false)

    val taskFlow = combine(searchText, sortBy, hideCompleted) {
        searchText, sortby, hideCompleted ->
        Triple(searchText, sortby, hideCompleted)
    }.flatMapLatest { (searchText, sortBy, hideCompleted) ->
        taskDao.getTasks(searchText, sortBy, hideCompleted)
    }

    val tasks = taskFlow.asLiveData()
}

enum class SortBy {
    SORT_BY_NAME, SORT_BY_DATE
}