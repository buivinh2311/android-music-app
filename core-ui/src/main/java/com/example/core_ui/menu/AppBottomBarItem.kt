package com.example.core_ui.menu

import androidx.compose.ui.graphics.painter.Painter

data class AppBottomBarItem  (
    val icon: Painter,
    val title: String,
    val action: AppBottomBarAction
)
