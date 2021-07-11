package com.example.pomodoroefes

import android.content.res.Resources
import android.graphics.drawable.AnimationDrawable
import android.os.CountDownTimer
import androidx.core.view.isInvisible
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

       // binding.restartButton.setOnClickListener { listener.reset(stopWatch.id) }

        binding.imageViewBin.setOnClickListener { listener.delete(stopWatch.id) }
    }

    private fun stopTimer(stopWatch: StopWatch) {
        timer?.cancel()

        binding.blinkingView.isInvisible = true
        (binding.blinkingView.background as? AnimationDrawable)?.stop()
    }

    private fun startTimer(stopWatch: StopWatch) {
       // val drawable = resources.getDrawable(R.drawable.actions_trash_empty_icon)
        binding.buttonStart.text = "Stop"

        timer?.cancel()
        timer = getCountDownTimer(stopWatch)
        timer?.start()


        binding.blinkingView.isInvisible = false
        (binding.blinkingView.background as? AnimationDrawable)?.start()
    }

    private fun getCountDownTimer(stopWatch: StopWatch): CountDownTimer {
        return object : CountDownTimer(PERIOD, UNIT_TEN_MS){
            val interval = UNIT_TEN_MS

            override fun onTick(millisUntilFinished: Long) {
                stopWatch.currentMs -= interval
                //custom view
                //set custom view
                binding.customViewTimer.setPeriod(PERIOD1)
                binding.customViewTimer.setCurrent(stopWatch.currentMs)
                //
                binding.timerText.text = stopWatch.currentMs.displayTime()
            }

            override fun onFinish() {
                binding.timerText.text = stopWatch.currentMs.displayTime()

            }

        }
    }

    private fun Long.displayTime(): String {
        if (this <= 0L) {
            return START_TIME
        }
        val h = this / 1000 / 3600
        val m = this / 1000 % 3600 / 60
        val s = this / 1000 % 60
      //  val ms = this % 1000 / 10

       // return "${displaySlot(h)}:${displaySlot(m)}:${displaySlot(s)}:${displaySlot(ms)}"
        return "${displaySlot(h)}:${displaySlot(m)}:${displaySlot(s)}"
    }

    private fun displaySlot(count: Long): String {
        return if (count / 10L > 0) {
            "$count"
        } else {
            "0$count"
        }
    }

    private companion object {
        private const val START_TIME = "00:00:00"
        private const val UNIT_TEN_MS = 1000L
        private const val PERIOD  = 1000L * 60L * 60L * 24L // Day
        private const val PERIOD1  = 1000L * 60L// Minute

    }
}

