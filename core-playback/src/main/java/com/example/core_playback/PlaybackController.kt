package com.example.core_playback

import com.example.core_model.Song
import com.example.core_playback.PlaybackState
import kotlinx.coroutines.flow.StateFlow

interface PlaybackController {
    val playbackState: StateFlow<PlaybackState>
    fun play(queueSource: String, queue: List<Song>, startSong: Song)
    fun pause()
    fun resume()
    fun seekTo(position: Long)
    fun skipNext()
    fun skipPrevious()
    fun toggleShuffle()
    fun changeRepeatMode()
}