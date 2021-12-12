package com.svmc.exampleapplication.luantv.ui.task

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.svmc.exampleapplication.R
import com.svmc.exampleapplication.databinding.FragmentMainMvvmBinding
import com.svmc.exampleapplication.luantv.util.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint
private const val TAG = "TaskFragment"
@AndroidEntryPoint
class TaskFragment: Fragment(R.layout.fragment_main_mvvm) {

    private val viewModel: TaskViewModel by viewModels()
    private lateinit var binding: FragmentMainMvvmBinding
    private lateinit var taskAdapter: TaskAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainMvvmBinding.bind(view)
        binding.root.findViewById<FloatingActionButton>(R.id.bnt_add)
            .setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_add, null))

        setHasOptionsMenu(true)
        taskAdapter = TaskAdapter()
        binding.apply {
            listTask.apply {
                adapter = taskAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

        }

        viewModel.tasks.observe(viewLifecycleOwner) {
            Log.d(TAG, " size ${it.size}")
            taskAdapter.submitList(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragmennt_task, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.onQueryTextChanged {
            //update text change
            viewModel.searchQuery.value = it
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_search -> {

                true
            }
            R.id.action_sort_by_date -> {

                true
            }
            R.id.action_sort_by_name -> {

                true
            }
            R.id.action_delete_all_completed_task -> {

                true
            }
            R.id.action_hide_completed_tasks -> {

                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}