package com.example.musicapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.core_model.Song
import com.example.feature_player.screen.PlayerScreen
import kotlinx.coroutines.flow.Flow

@Composable
fun PlayerRoute(
    songId: String,
    observeDownloadSong: (String) -> Flow<Boolean>,
    onSongOptionClick: (Song) -> Unit,
    onBackClick: () -> Unit,
    onViewArtistClick: (String) -> Unit,
    onDownloadClick: (Song) -> Unit,
    onViewQueueClick: () -> Unit
) {
    PlayerScreen(
        songId = songId,
        observeDownloadSong = observeDownloadSong,
        onSongOptionClick = onSongOptionClick,
        onBackClick = onBackClick,
        onViewArtistClick = onViewArtistClick,
        onDownloadClick = onDownloadClick,
        onViewQueueClick = onViewQueueClick
    )
}