package com.svmc.exampleapplication.luantv.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.svmc.exampleapplication.R
import dagger.hilt.android.AndroidEntryPoint

//import com.svmc.exampleapplication.databinding.ActivityMainBinding
//tools:viewBindingIgnore="true"
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
        setContentView(R.layout.activity_main)
    }
}