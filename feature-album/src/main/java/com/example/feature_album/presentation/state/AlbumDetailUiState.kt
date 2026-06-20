package com.example.feature_album.presentation.state

import com.example.core_model.Album
import com.example.core_model.Song
import com.example.core_ui.state.UiState

data class AlbumDetailUiState (
    val album: UiState<Album> = UiState.Loading,
    val songs: UiState<List<Song>> = UiState.Loading
)