package com.svmc.exampleapplication.luantv.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.svmc.exampleapplication.R

class EditFragment: Fragment(R.layout.edit_fragment_mvvm) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.edit_fragment_mvvm, container, false)
        root.findViewById<FloatingActionButton>(R.id.bnt_save).setOnClickListener {
//            take args
//            val args: TaskFragmentArgs by navArgs()
//            val taskArgs = args.addArgument

//            set args
            val number = 100
            val action = EditFragmentDirections.actionSave()
            findNavController().navigate(action)

        }
        return root
    }
}