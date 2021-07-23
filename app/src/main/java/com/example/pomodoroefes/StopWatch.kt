package com.example.pomodoroefes

data class StopWatch(
    val id:Int,
    var currentMs:Long,
    var isStarted:Boolean,
    val period:Long
)

