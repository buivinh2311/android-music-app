package com.example.feature_foryou.presentation

import com.example.core_model.DisplaySong

data class ForYouUiState (
    val songs: List<DisplaySong> = emptyList(),
    val isLoading: Boolean = false
)