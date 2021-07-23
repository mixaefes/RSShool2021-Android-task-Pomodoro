package com.example.pomodoroefes

interface StopwatchListener {

    fun start(id: Int)

    fun stop(id: Int, currentMs: Long)

    fun delete(id: Int)

    fun finish(id:Int)
}