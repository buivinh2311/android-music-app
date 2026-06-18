package com.example.feature_search.presentation

import com.example.core_model.Song

data class SearchUiState (
    val songs: List<Song> = emptyList(),
    val isLoading: Boolean = false
)