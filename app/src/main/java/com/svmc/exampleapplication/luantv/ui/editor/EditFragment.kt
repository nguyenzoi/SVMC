package com.svmc.exampleapplication.luantv.ui.editor

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.svmc.exampleapplication.R
import com.svmc.exampleapplication.databinding.EditFragmentMvvmBinding
import com.svmc.exampleapplication.luantv.util.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

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

            edtTaskTitle.addTextChangedListener {
                viewModel.taskName = it.toString()
            }

            checkBookImportance.setOnCheckedChangeListener { _, isChecked ->
                viewModel.taskImportance = isChecked
            }

            bntSave.setOnClickListener {
                viewModel.onSaveClicked()
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.editTaskEvent.collect { event ->
                when(event) {
                    is EditViewModel.AddEditTaskEvent.NotifyInvalidValue -> {
                        Snackbar.make(requireView(), event.text, Snackbar.LENGTH_LONG).show()
                    }
                    is EditViewModel.AddEditTaskEvent.NavigateBackWithResult ->  {
                        binding.edtTaskTitle.clearFocus()
                        setFragmentResult(
                            "add_update_request",
                            bundleOf("add_edit_result" to event.result)
                        )
                        findNavController().popBackStack()
                    }
                }.exhaustive
            }
        }
    }
}