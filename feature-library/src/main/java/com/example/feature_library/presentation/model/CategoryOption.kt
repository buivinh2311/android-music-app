package com.example.feature_library.presentation.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

data class CategoryOption(
    val icon: Painter,
    val title: String,
    val tint: Color,
    val onClick: () -> Unit
)