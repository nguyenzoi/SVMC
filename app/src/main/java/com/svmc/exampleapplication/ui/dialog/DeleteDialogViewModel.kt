package com.svmc.exampleapplication.ui.dialog

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.svmc.exampleapplication.data.TaskDao
import com.svmc.exampleapplication.dj.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DeleteDialogViewModel @ViewModelInject constructor(
    private val taskDao: TaskDao,
    @ApplicationScope private val applicationScope: CoroutineScope
): ViewModel() {

    fun onDeleteAllCompleted () = applicationScope.launch {
        taskDao.deleteAllCompletedTasks()
    }
}