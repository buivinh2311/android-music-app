package com.example.feature_player

import com.example.core_model.Song

data class PlayerUiState (
    val song: Song? = null,
    val isLoading: Boolean = false
)