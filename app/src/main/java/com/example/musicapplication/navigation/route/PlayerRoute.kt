package com.example.musicapplication.navigation.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.core_ui.data.AppBottomBarAction
import com.example.core_ui.data.SongOptionItem
import com.example.feature_foryou.presentation.screen.ForYouScreen
import com.example.feature_player.presentation.screen.PlayerScreen
import com.example.musicapplication.navigation.AppRoute

@Composable
fun PlayerRoute(
    songId: String,
    onBackClick: () -> Unit,
    onSongOptionClick: (SongOptionItem) -> Unit
) {
    PlayerScreen(
        songId = songId,
        onBackClick = onBackClick,
        onSongOptionClick = onSongOptionClick
    )
}