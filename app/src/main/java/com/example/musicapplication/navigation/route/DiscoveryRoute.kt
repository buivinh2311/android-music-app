package com.example.musicapplication.navigation.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.core_model.Song
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_discovery.presentation.DiscoveryScreen
import com.example.musicapplication.navigation.AppRoute
import com.example.shared_presentation.menu.SongOptionItem

@Composable
fun DiscoveryRoute(
    navController: NavController,
    onSongOptionClick: (Song) -> Unit,
    isConnect: Boolean,
    onSearchClick: () -> Unit,
    onSongClick: (String) -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    DiscoveryScreen(
        isConnect = isConnect,
        onSongOptionClick = onSongOptionClick,
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
        onBottomActionClick = onBottomActionClick
    )
}