package com.example.feature_discovery.presentation

import com.example.core_model.Artist
import com.example.core_model.Song
import com.example.core_ui.state.UiState

data class DiscoveryUiState (
    val hotArtists: UiState<List<Artist>> = UiState.Loading,
    val mostHeardSongs: UiState<List<Song>> = UiState.Loading,
    val forYouSongs: UiState<List<Song> > = UiState.Loading
)