package com.example.core_playback

import com.example.core_model.Song

enum class RepeatMode {
    OFF,
    ALL,
    ONE
}

data class PlaybackState (
    val queueSource: String = "_",
    val queue: List<Song> = emptyList(),
    val currentIndex: Int = 0,
    val currentSongId: String? = null,
    val isPlaying: Boolean = false,
    val currentPosition: Long = 0,
    val duration: Long = 0,
    val isShuffleEnabled: Boolean = false,
    val repeatMode: RepeatMode = RepeatMode.OFF
)