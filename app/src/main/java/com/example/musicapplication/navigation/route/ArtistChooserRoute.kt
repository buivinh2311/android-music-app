package com.example.musicapplication.navigation.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_artist.presentation.screen.ArtistChooserScreen
import com.example.musicapplication.navigation.AppRoute

@Composable
fun ArtistChooserRoute(
    artistStr: String,
    navController: NavController,
    onBottomActionClick: (AppBottomBarAction) -> Unit,
    onBackClick: () -> Unit
) {
    ArtistChooserScreen(
        artistStr = artistStr,
        onArtistClick = { artistName ->
            navController.navigate("${AppRoute.ARTIST_DETAIL}/$artistName")
        },
        onBackClick = onBackClick,
        onBottomActionClick = onBottomActionClick
    )
}