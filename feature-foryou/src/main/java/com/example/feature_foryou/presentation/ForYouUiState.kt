package com.example.feature_foryou.presentation

import com.example.core_model.Song

data class ForYouUiState (
    val songs: List<Song> = emptyList(),
    val isLoading: Boolean = false
)