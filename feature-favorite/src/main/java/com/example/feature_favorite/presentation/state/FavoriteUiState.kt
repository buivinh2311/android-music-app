package com.example.feature_favorite.presentation.state

import com.example.core_model.DisplaySong

data class FavoriteUiState (
    val favoriteSongs: List<DisplaySong> = emptyList(),
    val isLoading: Boolean = false
)