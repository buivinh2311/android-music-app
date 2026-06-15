package com.example.feature_home.presentation

import com.example.core_model.Album
import com.example.core_model.Song

data class HomeState (
    val hotAlbums: List<Album> = emptyList(),
    val recommendedSongs: List<Song> = emptyList(),
    val isLoading: Boolean = false
)