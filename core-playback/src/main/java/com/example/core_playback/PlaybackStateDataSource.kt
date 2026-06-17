package com.example.core_playback

interface PlaybackStateDataSource {
    fun loadLastPlaybackState(): PlaybackState
    fun saveLastPlaybackState(playbackState: PlaybackState)
}