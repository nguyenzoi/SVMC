package com.svmc.exampleapplication.collapsingtoolbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.svmc.exampleapplication.R
import kotlinx.android.synthetic.main.activity_collapsing_toolbar_demo.*

class CollapsingToolbarDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collapsing_toolbar_demo)

        setSupportActionBar(actd_toolbar)
    }
}