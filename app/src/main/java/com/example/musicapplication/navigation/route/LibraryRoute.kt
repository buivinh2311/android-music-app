package com.example.musicapplication.navigation.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_library.presentation.LibraryScreen
import com.example.musicapplication.navigation.AppRoute
import com.example.shared_presentation.model.SongOptionItem

@Composable
fun LibraryRoute(
    navController: NavController,
    onSearchClick: () -> Unit,
    onSongClick: (String) -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit,
    onSongNavigationAction: (SongOptionItem) -> Unit
) {
    LibraryScreen(
        onRecentClick = {
            navController.navigate(AppRoute.RECENT)
        },

        onFavoriteClick= {
            navController.navigate(AppRoute.FAVORITE)
        },

        onFavoriteAlbumClick = {
            navController.navigate(AppRoute.FAVORITE_ALBUM)
        },

        onFollowedArtistClick = {
            navController.navigate(AppRoute.FOLLOWED_ARTIST)
        },

        onMorePlaylistClick = {
            navController.navigate(AppRoute.PLAYLIST)
        },

        onMiniPlayerClick = { songId ->
            navController.navigate("${AppRoute.PLAYER}/$songId")
        },

        onPlaylistClick = { playlistId ->
            navController.navigate("${AppRoute.PLAYLIST_DETAIL}/$playlistId")
        },

        onSearchClick = onSearchClick,
        onSongClick = onSongClick,
        onBottomActionClick = onBottomActionClick,
        onSongNavigationAction = onSongNavigationAction
    )
}