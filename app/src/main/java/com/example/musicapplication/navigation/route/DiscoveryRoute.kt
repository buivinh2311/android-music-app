package com.example.musicapplication.navigation.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.core_ui.data.AppBottomBarAction
import com.example.core_ui.data.SongOptionItem
import com.example.feature_discovery.presentation.screen.DiscoveryScreen
import com.example.musicapplication.navigation.AppRoute

@Composable
fun DiscoveryRoute(
    navController: NavController,
    onSearchClick: () -> Unit,
    onSongClick: (String) -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit,
    onSongOptionClick: (SongOptionItem) -> Unit
) {
    DiscoveryScreen(
        onMoreArtistClick = {
            navController.navigate(AppRoute.ARTIST)
        },

        onArtistClick = { artistName ->
            navController.navigate("${AppRoute.ARTIST_DETAIL}/$artistName")
        },

        onForYouClick = {
            navController.navigate(AppRoute.FOR_YOU)
        },

        onMostListenedClick = {
            navController.navigate(AppRoute.MOST_LISTENED)
        },

        onSearchClick = onSearchClick,
        onSongClick = onSongClick,
        onBottomActionClick = onBottomActionClick,
        onSongOptionClick = onSongOptionClick
    )
}