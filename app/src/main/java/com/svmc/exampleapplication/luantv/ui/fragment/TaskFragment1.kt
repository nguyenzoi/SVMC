package com.svmc.exampleapplication.luantv.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.svmc.exampleapplication.R
import java.util.zip.Inflater

class TaskFragment1: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.test, container, false)
        root.findViewById<Button>(R.id.bnt_test)
            .setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_add1, null))
//            findNavController().navigate(R.id.action_add, null)

        return root
    }
}