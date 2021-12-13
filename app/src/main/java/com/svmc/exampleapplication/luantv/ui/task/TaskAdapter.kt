package com.svmc.exampleapplication.luantv.ui.task

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.svmc.exampleapplication.databinding.ItemTaskMvvmBinding
import com.svmc.exampleapplication.luantv.data.Task

class TaskAdapter(val listener: ItemListener): ListAdapter<Task, TaskAdapter.TaskHolder>(DiffCallBack()) {

    inner class TaskHolder(private val binding: ItemTaskMvvmBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.apply {
                itemView.setOnClickListener{
                    val task = getItem(adapterPosition)
                    listener.onItemClicked(task)
                }

                checkboxCompleted.setOnClickListener {
                    val task = getItem(adapterPosition)
                    val check = checkboxCompleted.isChecked
                    listener.onCheckBoxClicked(task, check)
                }
            }
        }

        fun bind(task: Task) {
            binding.apply {
                taskName.text = task.name
                checkboxCompleted.isChecked = task.completed
                taskName.paint.isStrikeThruText = task.completed
                labelPriority.isVisible = task.important
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val binding = ItemTaskMvvmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class DiffCallBack: DiffUtil.ItemCallback<Task> () {
        override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem

        override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id
    }

    interface ItemListener {
        fun onItemClicked (task: Task)

        fun onCheckBoxClicked (task: Task, checked: Boolean)
    }
}