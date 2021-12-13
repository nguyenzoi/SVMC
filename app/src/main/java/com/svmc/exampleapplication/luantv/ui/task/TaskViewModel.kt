package com.svmc.exampleapplication.luantv.ui.task

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.svmc.exampleapplication.luantv.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.Serializable

class TaskViewModel @ViewModelInject constructor(
    private val taskDao:TaskDao,
    private val preferenceManager: PreferenceManager
): ViewModel() {

    val searchQuery = MutableStateFlow("")
//    val orderBy = MutableStateFlow(Order.BY_DATE)
//    val hideCompleted = MutableStateFlow(false)
//
//    private val tasksFlow = combine(searchQuery, orderBy, hideCompleted) {
//        searchQuery, orderBy, hideCompleted -> Triple(searchQuery, orderBy, hideCompleted)
//    }.flatMapLatest { (searchQuery, orderBy, hideCompleted) ->
//        taskDao.getTask(searchQuery, orderBy, hideCompleted)
//    }.onCompletion {  }.catch {  }



    val preferenceFlow = preferenceManager.preferenceFlow

    val tasksFlow = combine(searchQuery, preferenceFlow) {
        searchQuery, filter -> Pair(searchQuery, filter)
    }.flatMapLatest { (searchQuery, filter) ->
        taskDao.getTask(searchQuery, filter.sortOrder, filter.hideCompleted)
    }

    val tasks = tasksFlow.asLiveData()

    fun updateSort(order: Order) = viewModelScope.launch {
        preferenceManager.updateSortOder(order)
    }

    fun updateHideCompleted (hide: Boolean) = viewModelScope.launch {
        preferenceManager.updateHideCompleted(hide)
    }

    fun updateTask(task: Task) {

    }

    fun updateCompletedTask(task: Task, completed: Boolean) = viewModelScope.launch {
        taskDao.update(task.copy(completed = completed))
    }

//    enum class Order {
//        BY_DATE, BY_NAME
//    }
}