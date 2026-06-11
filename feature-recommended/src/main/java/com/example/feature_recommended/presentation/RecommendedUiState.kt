package com.example.feature_recommended.presentation

import com.example.core_model.DisplaySong

data class RecommendedUiState (
    val songs: List<DisplaySong> = emptyList(),
    val isLoading: Boolean = false
)