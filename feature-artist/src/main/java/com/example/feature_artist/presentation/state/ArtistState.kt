package com.example.feature_artist.presentation.state

import com.example.core_model.Artist

data class ArtistState(
    val artists: List<Artist> = emptyList(),
    val isLoading: Boolean = false
)