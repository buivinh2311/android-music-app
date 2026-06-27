package com.example.musicapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.core_model.Song
import com.example.feature_player.PlayerScreen

@Composable
fun PlayerRoute(
    songId: String,
    onSongOptionClick: (Song) -> Unit,
    onBackClick: () -> Unit
) {
    PlayerScreen(
        songId = songId,
        onSongOptionClick = onSongOptionClick,
        onBackClick = onBackClick
    )
}