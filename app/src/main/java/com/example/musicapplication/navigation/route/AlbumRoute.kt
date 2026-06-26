package com.example.musicapplication.navigation.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_album.presentation.screen.AlbumScreen
import com.example.musicapplication.navigation.AppRoute

@Composable
fun AlbumRoute(
    onAlbumClick: (String) -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit,
    onBackClick: () -> Unit
) {
    AlbumScreen(
        onAlbumClick = onAlbumClick,
        onBottomActionClick = onBottomActionClick,
        onBackClick = onBackClick
    )
}