package com.example.pomodoroefes

import android.content.res.Resources
import android.graphics.drawable.AnimationDrawable
import android.os.CountDownTimer
import android.util.Log
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.pomodoroefes.databinding.RecyclerWatchItemBinding

class StopWatchViewHolder(
    private val binding: RecyclerWatchItemBinding,
    private val listener:StopwatchListener,
    private val resources: Resources
    ) :
    RecyclerView.ViewHolder(binding.root) {

    private var timer: CountDownTimer? = null

    fun bind(stopWatch: StopWatch) {
        binding.timerText.text = stopWatch.currentMs.displayTime()

        binding.customViewTimer.setPeriod(stopWatch.period)
        binding.customViewTimer.setCurrent(stopWatch.currentMs)
        binding.buttonStart.text = "START"

        if(stopWatch.isStarted){
            startTimer(stopWatch)
        }else{
            stopTimer(stopWatch)
        }
        initButtonsListeners(stopWatch)
    }

    private fun initButtonsListeners(stopWatch: StopWatch) {
        binding.buttonStart.setOnClickListener {
            if (stopWatch.isStarted) {
                listener.stop(stopWatch.id, stopWatch.currentMs)
            } else {
                listener.start(stopWatch.id)
            }
        }

        binding.imageViewBin.setOnClickListener { listener.delete(stopWatch.id) }
    }

    private fun startTimer(stopWatch: StopWatch) {
        binding.buttonStart.text = "STOP"

        Log.i("stopwatchviewholder", "startTimer is called period = ${stopWatch.period}")

        timer?.cancel()
        timer = getCountDownTimer(stopWatch)
        timer?.start()

        binding.customViewTimer.setPeriod(stopWatch.period)
        binding.blinkingView.isInvisible = false
        (binding.blinkingView.background as? AnimationDrawable)?.start()

    }

    private fun stopTimer(stopWatch: StopWatch) {
binding.buttonStart.text = "START"
        timer?.cancel()
        binding.blinkingView.isInvisible = true
        (binding.blinkingView.background as? AnimationDrawable)?.stop()
    }

    private fun getCountDownTimer(stopWatch: StopWatch): CountDownTimer {
        Log.i("Stopwatchviewholder", "getCountDownTimer is called")
        return object : CountDownTimer(stopWatch.currentMs, UNIT_TEN_MS){

            override fun onTick(millisUntilFinished: Long) {
                Log.i("StopWatchViewHolder", "getcountdowntimer is: ${millisUntilFinished*100}")
                binding.timerText.text = millisUntilFinished.displayTime()
                stopWatch.currentMs = millisUntilFinished
                binding.customViewTimer.setCurrent(millisUntilFinished)
            }

            override fun onFinish() {
                listener.finish(stopWatch.id)
                Log.i("Stopwatchviewholder", "on Finish is called, currentMs = ${stopWatch.currentMs}")
                binding.customViewTimer.setCurrent(0L)
               // binding.customViewTimer.isVisible = false
                binding.timerText.text = stopWatch.currentMs.displayTime()
                stopTimer(stopWatch)
                binding.buttonStart.text = "START"
                //listener.stop(stopWatch.id,stopWatch.currentMs)

            }

        }
    }

}

