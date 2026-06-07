package com.example.core_playback

import com.example.core_model.DisplaySong
import com.example.core_model.Song
import com.example.core_playback.state.PlaybackState
import kotlinx.coroutines.flow.StateFlow

interface PlaybackController {
    val playbackState: StateFlow<PlaybackState>
    fun play(song: Song)
    fun pause()
    fun resume()
}