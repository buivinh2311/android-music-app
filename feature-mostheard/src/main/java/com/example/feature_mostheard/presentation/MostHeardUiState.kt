package com.example.feature_mostheard.presentation

import com.example.core_model.DisplaySong

data class MostHeardUiState (
    val songs: List<DisplaySong> = emptyList(),
    val isLoading: Boolean = false
)