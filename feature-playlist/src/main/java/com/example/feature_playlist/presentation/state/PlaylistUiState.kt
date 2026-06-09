package com.example.feature_playlist.presentation.state

import com.example.core_model.Playlist

data class PlaylistUiState (
    val playlists: List<Playlist> = emptyList(),
    val isLoading: Boolean = false
)