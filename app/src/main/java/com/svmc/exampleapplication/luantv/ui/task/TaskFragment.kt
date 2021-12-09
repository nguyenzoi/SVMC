package com.svmc.exampleapplication.luantv.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.svmc.exampleapplication.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskFragment: Fragment(R.layout.fragment_main_mvvm) {

    private val viewModel: TaskViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main_mvvm, container, false)
        root.findViewById<FloatingActionButton>(R.id.bnt_add)
            .setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_add, null))
//            findNavController().navigate(R.id.action_add, null)

        return root
    }
}