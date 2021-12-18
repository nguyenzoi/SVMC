package com.svmc.exampleapplication.ui.tasks

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.svmc.exampleapplication.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TasksFragment: Fragment(R.layout.task_list_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}