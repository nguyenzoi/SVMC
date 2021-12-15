package com.svmc.exampleapplication.luantv.ui.task

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.svmc.exampleapplication.R
import com.svmc.exampleapplication.databinding.FragmentMainMvvmBinding
import com.svmc.exampleapplication.luantv.data.Order
import com.svmc.exampleapplication.luantv.data.Task
import com.svmc.exampleapplication.luantv.util.exhaustive
import com.svmc.exampleapplication.luantv.util.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

private const val TAG = "TaskFragment"
@AndroidEntryPoint
class TaskFragment: Fragment(R.layout.fragment_main_mvvm), TaskAdapter.ItemListener {

    private val viewModel: TaskViewModel by viewModels()
    private lateinit var binding: FragmentMainMvvmBinding
    private lateinit var taskAdapter: TaskAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainMvvmBinding.bind(view)

        setHasOptionsMenu(true)
        taskAdapter = TaskAdapter(this)
        binding.apply {
            listTask.apply {
                adapter = taskAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val task = taskAdapter.currentList[viewHolder.adapterPosition]
                    viewModel.onTaskSwiped(task)
                }
            }).attachToRecyclerView(listTask)

            bntAdd.setOnClickListener {
                viewModel.onClickAddButton()
            }

        }

        viewModel.tasks.observe(viewLifecycleOwner) {
            Log.d(TAG, " size ${it.size}")
            taskAdapter.submitList(it)
        }

        setFragmentResultListener("add_update_request") {_, bundle ->
            val result = bundle.getInt("add_edit_result")
            viewModel.onAddEditResult(result)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.tasksEvent.collect {event ->
                when(event) {
                    is TaskViewModel.TasksEvent.ShowUndoDeleteTaskMessage ->
                        Snackbar.make(requireView(), "Task deleted ", Snackbar.LENGTH_LONG)
                            .setAction("Undo") {
                                viewModel.onUndoDeleteSwiped(event.task)
                            }.show()
                    is TaskViewModel.TasksEvent.NavigateToAddScreen -> {
                        val action = TaskFragmentDirections.actionToAddEdit(null, title = "New Task")
                        findNavController().navigate(action)
                    }
                    is TaskViewModel.TasksEvent.NavigateToEditScreen -> {
                        val action = TaskFragmentDirections.actionToAddEdit(event.task, title = "Edit Task")
                        findNavController().navigate(action)
                    }
                    is TaskViewModel.TasksEvent.ShowAddEditResult -> {
                        Snackbar.make(requireView(), event.text, Snackbar.LENGTH_LONG).show()
                    }
                }.exhaustive
            }
        }
    }

    override fun onItemClicked(task: Task) {
        viewModel.onItemSelected(task)
    }

    override fun onCheckBoxClicked(task: Task, checked: Boolean) {
        viewModel.updateCompletedTask(task, checked)
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

            R.id.action_sort_by_date -> {
//                viewModel.orderBy.value = TaskViewModel.Order.BY_DATE
                viewModel.updateSort(Order.BY_DATE)
                true
            }
            R.id.action_sort_by_name -> {
//                viewModel.orderBy.value = TaskViewModel.Order.BY_NAME\
                viewModel.updateSort(Order.BY_NAME)
                true
            }
            R.id.action_delete_all_completed_task -> {

                true
            }
            R.id.action_hide_completed_tasks -> {
//                viewModel.hideCompleted.value = !viewModel.hideCompleted.value
//                need to get status of data preferece firstly
                item.isChecked = !item.isChecked
                viewModel.updateHideCompleted(item.isChecked)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}