package com.svmc.exampleapplication.ui.tasks

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.svmc.exampleapplication.data.Order
import com.svmc.exampleapplication.data.PreferenceManager
import com.svmc.exampleapplication.data.TaskDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class TaskViewModel @ViewModelInject constructor(
    private val taskDao: TaskDao,
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    val searchText = MutableStateFlow("")
    val dataStoreFlow = preferenceManager.dataStoreFlow

    val taskFlow = combine(searchText, dataStoreFlow) {
        searchText, filterPreference ->
        Pair(searchText, filterPreference)
    }.flatMapLatest { (searchText, filterPreference) ->
        taskDao.getTasks(searchText, filterPreference.order, filterPreference.hideCompleted)
    }

    val tasks = taskFlow.asLiveData()

    fun onSortUpdate (order: Order) = viewModelScope.launch {
        preferenceManager.updateSortBy(order)
    }

    fun onHideCompletedUpdated (status: Boolean) = viewModelScope.launch {
        preferenceManager.updateHideCompleted(status)
    }
}