package com.example.feature_home.presentation

import com.example.core_model.Album
import com.example.core_model.Song
import com.example.core_ui.state.UiState

data class HomeState (
    val hotAlbums: UiState<List<Album>> = UiState.Loading,
    val recommendedSongs: UiState<List<Song>> = UiState.Loading
)