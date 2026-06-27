package com.example.musicapplication.navigation.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.core_model.Song
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_artist.presentation.screen.ArtistDetailScreen
import com.example.musicapplication.navigation.AppRoute
import com.example.shared_presentation.menu.SongOptionItem

@Composable
fun ArtistDetailRoute(
    artistName: String,
    isConnect: Boolean,
    onSongClick: (String) -> Unit,
    onSongOptionClick: (Song) -> Unit,
    onBackClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    ArtistDetailScreen(
        artistName = artistName,
        isConnect = isConnect,
        onSongClick = onSongClick,
        onSongOptionClick = onSongOptionClick,
        onBackCLick = onBackClick,
        onBottomActionClick = onBottomActionClick
    )
}