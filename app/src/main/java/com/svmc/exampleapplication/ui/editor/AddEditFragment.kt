package com.svmc.exampleapplication.ui.editor

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.svmc.exampleapplication.R
import com.svmc.exampleapplication.databinding.AddEditFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.task_list_item.*

@AndroidEntryPoint
class AddEditFragment: Fragment(R.layout.add_edit_fragment) {

    private val viewModel: EditorViewModel by viewModels()
    private lateinit var binding: AddEditFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = AddEditFragmentBinding.bind(view)

        binding.apply {
            editTaskName.setText(viewModel.taskName)
            cbPriority.isChecked = viewModel.taskImportance
            cbPriority.jumpDrawablesToCurrentState()
            tvCreatedTime.isVisible = viewModel.task != null
            tvCreatedTime.text = "Created: ${viewModel.task?.created}"
        }
    }
}