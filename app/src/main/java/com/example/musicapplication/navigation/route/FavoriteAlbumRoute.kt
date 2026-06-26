package com.example.musicapplication.navigation.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_album.presentation.screen.FavoriteAlbumScreen
import com.example.musicapplication.navigation.AppRoute

@Composable
fun FavoriteAlbumRoute (
    onAlbumClick: (String) -> Unit,
    onBackClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    FavoriteAlbumScreen(
        onAlbumClick = onAlbumClick,
        onBackClick = onBackClick,
        onBottomActionClick = onBottomActionClick
    )
}