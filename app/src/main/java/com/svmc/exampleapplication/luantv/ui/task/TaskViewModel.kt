package com.svmc.exampleapplication.luantv.ui.task

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.svmc.exampleapplication.luantv.data.TaskDao
import kotlinx.coroutines.flow.*
import java.io.Serializable

class TaskViewModel @ViewModelInject constructor(
    private val taskDao:TaskDao
): ViewModel() {

    val searchQuery = MutableStateFlow("")
    val orderBy = MutableStateFlow(Order.BY_DATE)
    val hideCompleted = MutableStateFlow(false)

    private val tasksFlow = combine(searchQuery, orderBy, hideCompleted) {
        searchQuery, orderBy, hideCompleted -> Triple(searchQuery, orderBy, hideCompleted)
    }.flatMapLatest { (searchQuery, orderBy, hideCompleted) ->
        taskDao.getTask(searchQuery, orderBy, hideCompleted)
    }.onCompletion {  }.catch {  }


    val tasks = tasksFlow.asLiveData()

    enum class Order {
        BY_DATE, BY_NAME
    }
}