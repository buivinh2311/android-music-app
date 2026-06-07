package com.example.core_playback.state

data class PlaybackState (
    val currentSongId: String? = null,
    val isPlaying: Boolean = false,
    val currentPosition: Long = 0,
    val duration: Long = 0,
    val sourceUrl: String? = null
)