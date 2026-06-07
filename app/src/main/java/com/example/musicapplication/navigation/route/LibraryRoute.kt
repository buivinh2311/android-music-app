package com.example.musicapplication.navigation.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.core_ui.data.AppBottomBarAction
import com.example.core_ui.data.SongOptionItem
import com.example.feature_library.presentation.ui.LibraryScreen
import com.example.musicapplication.navigation.AppRoute

@Composable
fun LibraryRoute(
    navController: NavController,
    onSearchClick: () -> Unit,
    onSongClick: (String) -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit,
    onSongOptionClick: (SongOptionItem) -> Unit
) {
    LibraryScreen(
        onRecentClick = {
            navController.navigate(AppRoute.RECENT)
        },

        onFavoriteClick= {
            navController.navigate(AppRoute.FAVORITE)
        },

        onMorePlaylistClick = {
            navController.navigate(AppRoute.PLAYLIST)
        },

        onPlaylistClick = { playlistId ->
            navController.navigate("${AppRoute.PLAYLIST_DETAIL}/$playlistId")
        },

        onSearchClick = onSearchClick,
        onSongClick = onSongClick,
        onBottomActionClick = onBottomActionClick,
        onSongOptionClick = onSongOptionClick
    )
}