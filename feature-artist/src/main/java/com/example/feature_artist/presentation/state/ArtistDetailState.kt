package com.example.feature_artist.presentation.state

import com.example.core_model.Artist
import com.example.core_model.Song

data class ArtistDetailState (
    val artist: Artist? = null,
    val songs: List<Song> = emptyList(),
    val isLoading: Boolean = false
)