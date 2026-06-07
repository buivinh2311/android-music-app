package com.example.feature_album.presentation.state

import com.example.core_model.Album
import com.example.core_model.DisplaySong

data class AlbumDetailUiState (
    val album: Album? = null,
    val songs: List<DisplaySong> = emptyList(),
    val isLoading: Boolean = false
)