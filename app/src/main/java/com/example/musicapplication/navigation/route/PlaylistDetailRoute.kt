package com.example.musicapplication.navigation.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.core_model.Song
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_playlist.presentation.screen.PlaylistDetailScreen
import com.example.musicapplication.navigation.AppRoute
import com.example.shared_presentation.menu.SongOptionItem

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
        onBackCLick = onBackClick,
        onBottomActionClick = onBottomActionClick
    )
}