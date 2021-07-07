package com.example.pomodoroefes

import androidx.recyclerview.widget.RecyclerView
import com.example.pomodoroefes.databinding.RecyclerWatchItemBinding

class StopWatchViewHolder(private val binding: RecyclerWatchItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(stopWatch: StopWatch) {
        binding.timerText.text = stopWatch.currentMs.displayTime()
    }

    private fun Long.displayTime(): String {
        if (this <= 0L) {
            return START_TIME
        }
        val h = this / 1000 / 3600
        val m = this / 1000 % 3600 / 60
        val s = this / 1000 % 60
        val ms = this % 1000 / 10

        return "${displaySlot(h)}:${displaySlot(m)}:${displaySlot(s)}:${displaySlot(ms)}"
        return START_TIME
    }
    private fun displaySlot(count: Long): String {
        return if (count / 10L > 0) {
            "$count"
        } else {
            "0$count"
        }
    }

    private companion object {
        private const val START_TIME = "00:00:00:00"
    }
}

