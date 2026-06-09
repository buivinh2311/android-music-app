package com.example.feature_playlist.presentation.state

import com.example.core_model.DisplaySong
import com.example.core_model.Playlist

data class PlaylistDetailUiState (
    val playlist: Playlist? = null,
    val songs: List<DisplaySong> = emptyList(),
    val isLoading: Boolean = false
)