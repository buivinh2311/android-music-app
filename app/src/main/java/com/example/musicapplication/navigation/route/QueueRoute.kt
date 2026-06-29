package com.example.musicapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.core_model.Song
import com.example.feature_player.screen.QueueScreen

@Composable
fun QueueRoute(
    isConnect: Boolean,
    onSongOptionClick: (Song) -> Unit,
    onBackClick: () -> Unit
) {
    QueueScreen(
        isConnect = isConnect,
        onSongOptionClick = onSongOptionClick,
        onBackClick = onBackClick
    )
}