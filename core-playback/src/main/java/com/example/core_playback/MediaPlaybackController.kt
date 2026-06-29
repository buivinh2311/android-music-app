package com.example.core_playback

import com.example.core_model.playback.PlaybackState
import com.example.core_model.playback.QueueSource
import com.example.core_model.Song
import kotlinx.coroutines.flow.StateFlow

interface MediaPlaybackController {
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
    fun seekTo(mediaItemIndex: Int, position: Long)
    fun skipNext()
    fun skipPrevious()
    fun toggleShuffle()
    fun changeRepeatMode()
}