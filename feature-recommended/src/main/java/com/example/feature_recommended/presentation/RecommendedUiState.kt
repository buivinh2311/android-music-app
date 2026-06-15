package com.example.feature_recommended.presentation

import com.example.core_model.Song

data class RecommendedUiState (
    val songs: List<Song> = emptyList(),
    val isLoading: Boolean = false
)