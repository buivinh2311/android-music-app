package com.example.musicapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.core_model.Song
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_download.presentation.DownloadScreen

@Composable
fun DownloadRoute(
    selectedAction: AppBottomBarAction,
    onBackClick: () -> Unit,
    onSongClick: (String) -> Unit,
    onSongOptionClick: (Song) -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    DownloadScreen(
        selectedAction = selectedAction,
        onSongClick = onSongClick,
        onSongOptionClick = onSongOptionClick,
        onBackClick = onBackClick,
        onBottomActionClick = onBottomActionClick
    )
}