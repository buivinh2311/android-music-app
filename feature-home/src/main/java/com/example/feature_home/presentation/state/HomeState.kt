package com.example.feature_home.presentation.state

import com.example.core_model.Album
import com.example.core_model.DisplaySong

data class HomeState (
    val hotAlbums: List<Album> = emptyList(),
    val recommendedSongs: List<DisplaySong> = emptyList(),
    val isLoading: Boolean = false
)