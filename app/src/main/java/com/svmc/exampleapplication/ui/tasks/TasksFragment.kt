package com.svmc.exampleapplication.ui.tasks

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleObserver
import androidx.recyclerview.widget.LinearLayoutManager
import com.svmc.exampleapplication.R
import com.svmc.exampleapplication.databinding.TaskListFragmentBinding
import com.svmc.exampleapplication.util.onQueryChanged
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

        viewModel.tasks.observe(viewLifecycleOwner) {
            taskAdapter.submitList(it)
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.task_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.onQueryChanged {
            viewModel.searchText.value = it
        }

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_sort_by_name -> {

                return true
            }
            R.id.action_sort_by_date -> {

                return true
            }

            R.id.action_delete_all_completed_task -> {

                return true
            }

            R.id.action_hide_all_completed_task -> {

                return true
            }

        }
        return super.onContextItemSelected(item)
    }
}