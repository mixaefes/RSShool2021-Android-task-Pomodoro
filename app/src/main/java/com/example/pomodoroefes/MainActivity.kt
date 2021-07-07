package com.example.pomodoroefes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pomodoroefes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private val stopWatches = mutableListOf<StopWatch>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
    }
}