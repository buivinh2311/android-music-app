package com.example.musicapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_artist.presentation.screen.ArtistScreen


@Composable
fun ArtistRoute(
    selectedAction: AppBottomBarAction,
    onArtistClick: (String) -> Unit,
    onBackClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    ArtistScreen(
        selectedAction = selectedAction,
        onArtistClick = onArtistClick,
        onBackCLick = onBackClick,
        onBottomActionClick = onBottomActionClick
    )
}