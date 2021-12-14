package com.svmc.exampleapplication.ui.tasks

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.svmc.exampleapplication.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TasksFragment : Fragment(R.layout.task_fragment) {
    private val viewModel: TaskViewModel by viewModels()
}