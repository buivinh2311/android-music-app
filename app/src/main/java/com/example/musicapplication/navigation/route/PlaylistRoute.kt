package com.example.musicapplication.navigation.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_playlist.presentation.screen.PlaylistScreen
import com.example.musicapplication.navigation.AppRoute

@Composable
fun PlaylistRoute(
    onPlaylistClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    PlaylistScreen(
        onPlaylistClick = onPlaylistClick,
        onBackClick = onBackClick,
        onBottomActionClick = onBottomActionClick
    )
}