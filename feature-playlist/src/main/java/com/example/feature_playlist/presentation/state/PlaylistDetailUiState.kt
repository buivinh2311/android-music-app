package com.example.feature_playlist.presentation.state

import com.example.core_model.Song
import com.example.core_model.Playlist

data class PlaylistDetailUiState (
    val playlist: Playlist? = null,
    val songs: List<Song> = emptyList(),
    val isLoading: Boolean = false
)