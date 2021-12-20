package com.svmc.exampleapplication.ui.tasks

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.svmc.exampleapplication.R
import com.svmc.exampleapplication.data.Order
import com.svmc.exampleapplication.data.Task
import com.svmc.exampleapplication.databinding.TaskListFragmentBinding
import com.svmc.exampleapplication.util.exhaustive
import com.svmc.exampleapplication.util.onQueryChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TasksFragment: Fragment(R.layout.task_list_fragment), TaskAdapter.TaskItemListener{
    private val viewModel: TaskViewModel by viewModels()
    private lateinit var binding: TaskListFragmentBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = TaskListFragmentBinding.bind(view)

        val taskAdapter = TaskAdapter(this)
        binding.apply {
            listTask.apply {
                adapter = taskAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    viewModel.OnSwipeToDeleteItem(taskAdapter.currentList[viewHolder.adapterPosition])
                }
            }).attachToRecyclerView(listTask)
        }

        viewModel.tasks.observe(viewLifecycleOwner) {
            taskAdapter.submitList(it)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.taskEvent.collect {event ->
                when(event) {
                    is TaskViewModel.TaskEvent.OnAddEditScreen -> {
                        TODO()
                    }
                    is TaskViewModel.TaskEvent.OnUpdateHideCompletedTask -> {
                        taskAdapter
                    }
                    is TaskViewModel.TaskEvent.OnUndoTask -> {
                        Snackbar.make(requireView(),"Deleted Task", Snackbar.LENGTH_LONG)
                            .setAction("Undo") {
                                viewModel.undoTask(event.task)
                            }.show()
                    }
                }.exhaustive
            }
        }
        setHasOptionsMenu(true)
    }

    override fun onClickItem(task: Task) {
        viewModel.onClickItem(task)
    }

    override fun onCheckBoxClick(task: Task, isChecked: Boolean) {
        viewModel.onClickCheckBoxCompleted(task, isChecked)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.task_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.onQueryChanged {
            viewModel.searchText.value = it
        }

        viewLifecycleOwner.lifecycleScope.launch {
            menu.findItem(R.id.action_hide_all_completed_task)
                .isChecked = viewModel.dataStoreFlow.first().hideCompleted
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_sort_by_name -> {
                viewModel.onSortUpdate(Order.SORT_BY_NAME)
                return true
            }
            R.id.action_sort_by_date -> {
                viewModel.onSortUpdate(Order.SORT_BY_DATE)
                return true
            }

            R.id.action_delete_all_completed_task -> {

                return true
            }

            R.id.action_hide_all_completed_task -> {
                item.isChecked = !item.isChecked
                viewModel.onHideCompletedUpdated(item.isChecked)
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }
}