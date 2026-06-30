package com.example.musicapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_playlist.presentation.screen.PlaylistScreen

@Composable
fun PlaylistRoute(
    selectedAction: AppBottomBarAction,
    onPlaylistClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    PlaylistScreen(
        selectedAction = selectedAction,
        onPlaylistClick = onPlaylistClick,
        onBackClick = onBackClick,
        onBottomActionClick = onBottomActionClick
    )
}