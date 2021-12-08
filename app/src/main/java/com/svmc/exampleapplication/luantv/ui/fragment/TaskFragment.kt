package com.svmc.exampleapplication.luantv.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.svmc.exampleapplication.R
import java.util.zip.Inflater

class TaskFragment: Fragment(R.layout.fragment_main_mvvm) {

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