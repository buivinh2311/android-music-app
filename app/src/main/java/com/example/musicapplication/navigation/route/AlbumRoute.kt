package com.example.musicapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_album.presentation.screen.AlbumScreen

@Composable
fun AlbumRoute(
    selectedAction: AppBottomBarAction,
    onAlbumClick: (String) -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit,
    onBackClick: () -> Unit
) {
    AlbumScreen(
        selectedAction = selectedAction,
        onAlbumClick = onAlbumClick,
        onBottomActionClick = onBottomActionClick,
        onBackClick = onBackClick
    )
}