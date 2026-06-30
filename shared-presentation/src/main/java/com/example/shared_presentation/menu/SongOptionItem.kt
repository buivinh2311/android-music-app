package com.example.shared_presentation.menu

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

data class SongOptionItem (
    val id: String,
    val artist: String? = null,
    val album: String? = null,
    val icon: Painter,
    val iconColor: Color,
    val title: String,
    val action: SongOptionAction
)
