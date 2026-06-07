package com.example.feature_artist.presentation.state

import com.example.core_model.Artist
import com.example.core_model.DisplaySong

data class ArtistDetailState (
    val artist: Artist? = null,
    val songs: List<DisplaySong> = emptyList(),
    val isLoading: Boolean = false
)