package com.example.core_ui.data

import androidx.compose.ui.graphics.painter.Painter

data class SongOptionItem(
    val id: String,
    val artist: String? = null,
    val album: String? = null,
    val icon: Painter,
    val title: String,
    val action: SongOptionAction
)
