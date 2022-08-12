package com.example.shortcutsandwidgets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.shortcutsandwidgets.databinding.ActivityMessageBinding

class MessageActivity : AppCompatActivity() {

    private lateinit var vBinding: ActivityMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vBinding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(vBinding.root)
    }
}