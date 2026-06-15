package com.example.feature_mostheard.presentation

import com.example.core_model.Song

data class MostHeardUiState (
    val songs: List<Song> = emptyList(),
    val isLoading: Boolean = false
)