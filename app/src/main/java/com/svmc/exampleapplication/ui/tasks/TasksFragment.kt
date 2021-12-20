package com.svmc.exampleapplication.ui.tasks

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleObserver
import androidx.recyclerview.widget.LinearLayoutManager
import com.svmc.exampleapplication.R
import com.svmc.exampleapplication.databinding.TaskListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TasksFragment: Fragment(R.layout.task_list_fragment) {
    private val viewModel: TaskViewModel by viewModels()
    private lateinit var binding: TaskListFragmentBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = TaskListFragmentBinding.bind(view)

        val taskAdapter = TaskAdapter()
        binding.apply {
            listTask.apply {
                adapter = taskAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }

        viewModel.task.observe(viewLifecycleOwner) {
            taskAdapter.submitList(it)
        }
    }
}