package com.example.musicapplication.navigation.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_artist.presentation.screen.ArtistScreen
import com.example.musicapplication.navigation.AppRoute


@Composable
fun ArtistRoute(
    onArtistClick: (String) -> Unit,
    onBackClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    ArtistScreen(
        onArtistClick = onArtistClick,
        onBackCLick = onBackClick,
        onBottomActionClick = onBottomActionClick
    )
}