package com.example.musicapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.core_model.Song
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_favorite.presentation.FavoriteScreen

@Composable
fun FavoriteRoute(
    isConnect: Boolean,
    selectedAction: AppBottomBarAction,
    onBackClick: () -> Unit,
    onSongClick: (String) -> Unit,
    onSongOptionClick: (Song) -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    FavoriteScreen(
        isConnect = isConnect,
        selectedAction = selectedAction,
        onSongClick = onSongClick,
        onSongOptionClick = onSongOptionClick,
        onBackCLick = onBackClick,
        onBottomActionClick = onBottomActionClick
    )
}