package com.svmc.exampleapplication.ui.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.svmc.exampleapplication.data.Task
import com.svmc.exampleapplication.databinding.TaskListItemBinding

class TaskAdapter(val listener: TaskItemListener): ListAdapter<Task, TaskAdapter.TaskViewHolder>(CallBackDiff()) {

    inner class TaskViewHolder(private val binding: TaskListItemBinding): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val pos = adapterPosition
                    if (pos != RecyclerView.NO_POSITION)
                        listener.onClickItem(getItem(pos))
                }
                checkboxCompleted.setOnClickListener {
                    val pos = adapterPosition
                    if (pos != RecyclerView.NO_POSITION) {
                        val task = getItem(pos)
                        listener.onCheckBoxClick(task, checkboxCompleted.isChecked)
                    }
                }
            }
        }

        fun bind(task: Task) {
            binding.apply {
                tvTaskName.text = task.name
                imagePriority.isVisible = task.importance
                tvTaskName.paint.isStrikeThruText = task.completed
                checkboxCompleted.isChecked = task.completed
            }
        }
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = TaskListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    class CallBackDiff: DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem.hashCode() == newItem.hashCode()
    }

    interface TaskItemListener {
        fun onClickItem(task: Task)
        fun onCheckBoxClick (task: Task, isChecked: Boolean)
    }

}