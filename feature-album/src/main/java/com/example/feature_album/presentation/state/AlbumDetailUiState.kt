package com.example.feature_album.presentation.state

import com.example.core_model.Album
import com.example.core_model.Song

data class AlbumDetailUiState (
    val album: Album? = null,
    val songs: List<Song> = emptyList(),
    val isLoading: Boolean = false
)