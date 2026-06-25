package com.example.musicapplication.navigation.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_playlist.presentation.screen.PlaylistScreen
import com.example.musicapplication.navigation.AppRoute

@Composable
fun PlaylistRoute(
    navController: NavController,
    onBackClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    PlaylistScreen(
        onPlaylistClick = { playlistId ->
            navController.navigate("${AppRoute.PLAYLIST_DETAIL}/$playlistId")
        },
        onBackClick = onBackClick,
        onBottomActionClick = onBottomActionClick
    )
}