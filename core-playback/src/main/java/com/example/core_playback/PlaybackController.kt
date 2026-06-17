package com.example.core_playback

import androidx.media3.common.Player
import com.example.core_model.Song
import com.example.core_playback.PlaybackState
import kotlinx.coroutines.flow.StateFlow

interface PlaybackController {
    val playbackState: StateFlow<PlaybackState>
    fun play(
        queueSource: QueueSource,
        queue: List<Song>,
        startSong: Song,
        playlistId: Int? = 0,
        sourceName: String? = null
    )
    fun pause()
    fun resume()
    fun seekTo(position: Long)
    fun skipNext()
    fun skipPrevious()
    fun toggleShuffle()
    fun changeRepeatMode()
}