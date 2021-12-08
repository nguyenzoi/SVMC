package com.svmc.exampleapplication.nguyenlv.ui.addedittask

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
import com.svmc.exampleapplication.databinding.FragmentAddEditTaskBinding
import com.svmc.exampleapplication.nguyenlv.util.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_edit_task.*
import kotlinx.android.synthetic.main.fragment_tasks.*
import kotlinx.coroutines.flow.collect

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

            faetName.addTextChangedListener {
                viewModel.taskName = it.toString()
            }

            faetCb.setOnCheckedChangeListener { buttonView, isChecked ->
                viewModel.taskImportance = isChecked
            }

            faetSave.setOnClickListener { viewModel.onSaveClick() }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.addEditTaskEvent.collect { event ->
                when (event) {
                    is AddEditTaskViewModel.AddEditTaskEvent.ShowInvalidInputMessage -> {
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_LONG).show()
                    }
                    is AddEditTaskViewModel.AddEditTaskEvent.NavigateBackWithResult -> {
                        binding.faetName.clearFocus()
                        setFragmentResult(
                            "add_edit_request",
                            bundleOf("add_edit_result" to event.result)
                        )
                        findNavController().popBackStack()
                    }
                }.exhaustive
            }
        }
    }
}