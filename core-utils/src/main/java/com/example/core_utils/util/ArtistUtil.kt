package com.example.core_utils.util

object ArtistUtil {
    fun interestedToString(interested: Int?): String {
        if(interested == null) return "0 "
        if(interested < 1000) {
            return "$interested "
        } else  {
            val value = interested/1000f
            return value.toString() + "K "
        }
    }
}