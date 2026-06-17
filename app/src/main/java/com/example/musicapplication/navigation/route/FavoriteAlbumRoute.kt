package com.example.musicapplication.navigation.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_album.presentation.screen.FavoriteAlbumScreen
import com.example.musicapplication.navigation.AppRoute

@Composable
fun FavoriteAlbumRoute (
    navController: NavController,
    onBackClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    FavoriteAlbumScreen(
        onAlbumClick = { albumName ->
            navController.navigate("${AppRoute.ALBUM_DETAIL}/${albumName}")
        },
        onMiniPlayerClick = { songId ->
            navController.navigate("${AppRoute.PLAYER}/$songId")
        },
        onBackClick = onBackClick,
        onBottomActionClick = onBottomActionClick
    )
}