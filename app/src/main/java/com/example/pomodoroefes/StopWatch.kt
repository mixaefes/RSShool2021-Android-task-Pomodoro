package com.example.pomodoroefes

data class StopWatch(
    val id:Int,
    var currentMs:Long,
    val isStarted:Boolean
)
