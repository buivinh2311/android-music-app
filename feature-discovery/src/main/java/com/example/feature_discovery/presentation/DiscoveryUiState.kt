package com.example.feature_discovery.presentation

import com.example.core_model.Artist
import com.example.core_model.Song

data class DiscoveryUiState (
    val hotArtists: List<Artist> = emptyList(),
    val mostHeardSongs: List<Song> = emptyList(),
    val forYouSongs: List<Song> = emptyList(),
    val isLoading: Boolean = false
)