package com.example.feature_discovery.presentation

import com.example.core_model.Artist
import com.example.core_model.DisplaySong

data class DiscoveryUiState (
    val hotArtists: List<Artist> = emptyList(),
    val mostHeardSongs: List<DisplaySong> = emptyList(),
    val forYouSongs: List<DisplaySong> = emptyList(),
    val isLoading: Boolean = false
)