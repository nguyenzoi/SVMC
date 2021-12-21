package com.svmc.exampleapplication.ui.editor

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
import com.svmc.exampleapplication.databinding.AddEditFragmentBinding
import com.svmc.exampleapplication.util.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.task_list_item.*
import kotlinx.coroutines.flow.collect

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

            floatSave.setOnClickListener {
                viewModel.onSaveButton()
            }

            editTaskName.addTextChangedListener {
                viewModel.taskName = it.toString()
            }

            cbPriority.setOnCheckedChangeListener {_, checked->
                viewModel.taskImportance = checked
            }

        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.editorEventFlow.collect {event ->
                when(event) {
                    is EditorViewModel.AddEditEvent.NavigateBackWithResult -> {
                        setFragmentResult(
                            "add_edit_request", bundleOf("add_edit_result" to event.result))
                        binding.editTaskName.clearFocus()
                        findNavController().popBackStack()
                    }
                    is EditorViewModel.AddEditEvent.Fail -> {
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_LONG).show()
                    }
                }.exhaustive
            }
        }
    }
}