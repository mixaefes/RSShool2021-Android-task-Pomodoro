package com.example.pomodoroefes

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pomodoroefes.databinding.RecyclerWatchItemBinding

class StopwatchAdapter: ListAdapter<StopWatch, StopWatchViewHolder>(itemComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StopWatchViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerWatchItemBinding.inflate(layoutInflater, parent, false)
        return StopWatchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StopWatchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private companion object {

        private val itemComparator = object : DiffUtil.ItemCallback<StopWatch>() {

            override fun areItemsTheSame(oldItem: StopWatch, newItem: StopWatch): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: StopWatch, newItem: StopWatch): Boolean {
                return oldItem.currentMs == newItem.currentMs &&
                        oldItem.isStarted == newItem.isStarted
            }
        }
    }
}