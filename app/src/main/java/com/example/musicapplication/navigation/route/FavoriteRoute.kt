package com.example.musicapplication.navigation.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_favorite.presentation.FavoriteScreen
import com.example.musicapplication.navigation.AppRoute
import com.example.shared_presentation.menu.SongOptionItem

@Composable
fun FavoriteRoute(
    isConnect: Boolean,
    onBackClick: () -> Unit,
    onSongClick: (String) -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit,
    onSongNavigationAction: (SongOptionItem) -> Unit
) {
    FavoriteScreen(
        isConnect = isConnect,
        onSongClick = onSongClick,
        onBackCLick = onBackClick,
        onBottomActionClick = onBottomActionClick,
        onSongNavigationAction = onSongNavigationAction
    )
}