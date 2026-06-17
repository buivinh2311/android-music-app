package com.example.musicapplication.navigation.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_artist.presentation.screen.ArtistScreen
import com.example.musicapplication.navigation.AppRoute


@Composable
fun ArtistRoute(
    navController: NavController,
    onBackClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    ArtistScreen(
        onArtistClick = { artistName ->
            navController.navigate("${AppRoute.ARTIST_DETAIL}/$artistName")
        },
        onMiniPlayerClick = { songId ->
            navController.navigate("${AppRoute.PLAYER}/$songId")
        },
        onBackCLick = onBackClick,
        onBottomActionClick = onBottomActionClick
    )
}