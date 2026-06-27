package com.example.core_playback

import com.example.core_model.playback.PlaybackState

interface PlaybackStateDataSource {
    fun loadLastPlaybackState(): PlaybackState
    fun saveLastPlaybackState(playbackState: PlaybackState)
}