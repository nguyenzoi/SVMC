package com.svmc.exampleapplication.luantv.ui.dialog

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.svmc.exampleapplication.luantv.data.TaskDao
import com.svmc.exampleapplication.luantv.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DeleteDialogViewModel @ViewModelInject constructor(
    val taskDao: TaskDao,
    @ApplicationScope val applicationScope: CoroutineScope
): ViewModel() {

    fun deleteAllCompleted() = applicationScope.launch {
        taskDao.deleteAllCompleted()
    }
}