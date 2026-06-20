package com.example.feature_artist.presentation.state

import com.example.core_model.Artist
import com.example.core_model.Song
import com.example.core_ui.state.UiState

data class ArtistDetailState (
    val artist: UiState<Artist> = UiState.Loading,
    val songs: UiState<List<Song>> = UiState.Loading
)