package com.example.core_model

data class PlaybackState (
    val queueSource: QueueSource = QueueSource.DEFAULT,
    val queue: List<Song> = emptyList(),
    val currentIndex: Int = 0,
    val currentSongId: String? = null,
    val isPlaying: Boolean = false,
    val currentPosition: Long = 0,
    val artistName: String? = null,
    val albumName: String? = null,
    val playlistId: Int = 0,
    val sourceName: String? = null,
    val duration: Long = 0,
    val isShuffleEnabled: Boolean = false,
    val repeatMode: RepeatMode = RepeatMode.OFF
)