package com.example.musicapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.core_model.Song
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_playlist.presentation.screen.PlaylistDetailScreen

@Composable
fun PlaylistDetailRoute(
    playlistId: Int,
    isConnect: Boolean,
    onSongClick: (String) -> Unit,
    onSongOptionClick: (Song) -> Unit,
    onBackClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    PlaylistDetailScreen(
        playlistId = playlistId,
        isConnect = isConnect,
        onSongClick = onSongClick,
        onSongOptionClick = onSongOptionClick,
        onBackClick = onBackClick,
        onBottomActionClick = onBottomActionClick
    )
}