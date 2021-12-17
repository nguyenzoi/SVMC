package com.nguyenle.mvvmtodo.ui.deleteallcompleted

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nguyenle.mvvmtodo.data.TaskDao
import kotlinx.coroutines.launch

class DeleteAllCompletedDialogViewModel @ViewModelInject constructor(
    private val taskDao: TaskDao
) : ViewModel() {

    fun onConfirmClick(){
        deleteAllCompleted()
    }
    fun deleteAllCompleted() = viewModelScope.launch {
        taskDao.deleteCompletedTasks()
    }
}