package com.svmc.exampleapplication.nguyenlv.ui.deleteallcompleted

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.svmc.exampleapplication.nguyenlv.data.TaskDao
import com.svmc.exampleapplication.nguyenlv.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DeleteAllCompletedViewModel @ViewModelInject constructor(
    private val taskDao: TaskDao,
    @ApplicationScope private val applicationScope: CoroutineScope
) : ViewModel() {
    fun onConfirmClick() = applicationScope.launch { taskDao.deleteCompletedTasks() }
}