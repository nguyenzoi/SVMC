package com.svmc.exampleapplication.nguyenlv.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.svmc.exampleapplication.databinding.ActivityMvvmDemoBinding

class MvvmDemoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMvvmDemoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMvvmDemoBinding.inflate(layoutInflater);
        setContentView(binding.root)
    }
}