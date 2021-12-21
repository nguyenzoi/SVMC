package com.svmc.exampleapplication.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteCompletedTaskFragment: DialogFragment() {

    private val viewModel: DeleteDialogViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext(), -1)
            .setTitle("Delete all completed tasks")
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Ok") {_,_ ->
                viewModel.onDeleteAllCompleted()
            }.create()
}