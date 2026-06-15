package com.example.core_utils.util

object PlayerUtil {
    fun durationToString(duration: Long?): String {
        if(duration == null) return "0:00"
        val minute = (duration/60).toString()
        var second = (duration % 60).toString()
        if(second.length == 1) {
            second = "0$second"
        }
        return "$minute:$second"
    }
}