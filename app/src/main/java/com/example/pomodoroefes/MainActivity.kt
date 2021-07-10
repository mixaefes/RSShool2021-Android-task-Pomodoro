package com.example.pomodoroefes

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pomodoroefes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),StopwatchListener{
    private lateinit var binding: ActivityMainBinding
    private val stopWatches = mutableListOf<StopWatch>()
    private val stopwatchAdapter = StopwatchAdapter(this)
    private var nextId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonAdd.isEnabled = false
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = stopwatchAdapter

        }
        binding.timeToSetEditText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.i("MainActivity", "onTextChanged is called")
                binding.buttonAdd.isEnabled = true
            }

            override fun afterTextChanged(s: Editable?) {
                TODO("Not yet implemented")
            }


        })
        binding.buttonAdd.setOnClickListener {
            if(binding.timeToSetEditText.text.isNotEmpty()) {
                val timeToSet = Integer.parseInt(binding.timeToSetEditText.text.toString())
                stopWatches.add(StopWatch(nextId++, (timeToSet * 1000 * 60).toLong(), false))
                stopwatchAdapter.submitList(stopWatches.toList())
                hideKeyboard(this)
                binding.timeToSetEditText.text.clear()
                binding.buttonAdd.isEnabled = false
            }else{
                Toast.makeText(this, "Set timer in minutes", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun start(id: Int) {
        changeStopwatch(id, null, true)
    }

    override fun stop(id: Int, currentMs: Long) {
        changeStopwatch(id, currentMs, false)
    }

    override fun reset(id: Int) {
        changeStopwatch(id, 0L, false)
    }

    override fun delete(id: Int) {
        stopWatches.remove(stopWatches.find { it.id == id })
        stopwatchAdapter.submitList(stopWatches.toList())
    }
    private fun changeStopwatch(id: Int, currentMs: Long?, isStarted: Boolean) {
        val newTimers = mutableListOf<StopWatch>()
        stopWatches.forEach {
            if (it.id == id) {
                newTimers.add(StopWatch(it.id, currentMs ?: it.currentMs, isStarted))
            } else {
                newTimers.add(it)
            }
        }
        stopwatchAdapter.submitList(newTimers)
        stopWatches.clear()
        stopWatches.addAll(newTimers)
    }
   private fun hideKeyboard(context: Context){
       // val view = activity.currentFocus?: View(activity)
       val inputMethod:InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

       inputMethod.hideSoftInputFromWindow(this.currentFocus?.windowToken,InputMethodManager.HIDE_NOT_ALWAYS)
    }
}