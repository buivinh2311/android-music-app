package com.example.feature_playlist.menu

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

data class PlaylistOptionItem(
    val id: Int,
    val icon: Painter,
    val iconColor: Color,
    val title: String,
    val action: PlaylistOptionAction
)