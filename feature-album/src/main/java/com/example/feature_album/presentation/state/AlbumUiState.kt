package com.example.feature_album.presentation.state

import com.example.core_model.Album


data class AlbumUiState (
    val albums: List<Album> = emptyList(),
    val isLoading: Boolean = false
)