package com.example.musicapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.feature_player.presentation.screen.PlayerScreen
import com.example.shared_presentation.model.SongOptionItem

@Composable
fun PlayerRoute(
    songId: String,
    onBackClick: () -> Unit,
    onSongNavigationAction: (SongOptionItem) -> Unit
) {
    PlayerScreen(
        songId = songId,
        onBackClick = onBackClick,
        onSongNavigationAction = onSongNavigationAction
    )
}