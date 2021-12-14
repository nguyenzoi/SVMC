package com.svmc.exampleapplication.luantv.ui.editor

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.svmc.exampleapplication.R
import com.svmc.exampleapplication.databinding.EditFragmentMvvmBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.item_task_mvvm.*

@AndroidEntryPoint
class EditFragment: Fragment(R.layout.edit_fragment_mvvm) {
    private val viewModel: EditViewModel by viewModels()
    lateinit var binding: EditFragmentMvvmBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = EditFragmentMvvmBinding.bind(view)

        binding.apply {
            edtTaskTitle.setText(viewModel.taskName)
            checkBookImportance.isChecked = viewModel.taskImportance
            checkBookImportance.jumpDrawablesToCurrentState()
            tvCreatedDate.isVisible = viewModel.task != null
            tvCreatedDate.text = viewModel.task?.createdDateFormat
        }
    }
}