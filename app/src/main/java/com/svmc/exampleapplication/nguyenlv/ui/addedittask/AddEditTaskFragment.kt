package com.svmc.exampleapplication.nguyenlv.ui.addedittask

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.svmc.exampleapplication.R
import com.svmc.exampleapplication.databinding.FragmentAddEditTaskBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_edit_task.*

@AndroidEntryPoint
class AddEditTaskFragment : Fragment(R.layout.fragment_add_edit_task) {
    private val viewModel: AddEditTaskViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentAddEditTaskBinding.bind(view)

        binding.apply {
            faetName.setText(viewModel.taskName)
            faetCb.isChecked = viewModel.taskImportance
            faetCb.jumpDrawablesToCurrentState()
            faetDateCreated.isVisible = viewModel.task != null
            faetDateCreated.text = "Created: ${viewModel.task?.createdDateFormatted}"
        }
    }
}