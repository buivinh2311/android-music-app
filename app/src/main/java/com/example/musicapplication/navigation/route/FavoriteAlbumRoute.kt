package com.example.musicapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_album.presentation.screen.FavoriteAlbumScreen

@Composable
fun FavoriteAlbumRoute (
    selectedAction: AppBottomBarAction,
    onAlbumClick: (String) -> Unit,
    onBackClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    FavoriteAlbumScreen(
        selectedAction = selectedAction,
        onAlbumClick = onAlbumClick,
        onBackClick = onBackClick,
        onBottomActionClick = onBottomActionClick
    )
}