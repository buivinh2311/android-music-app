package com.example.musicapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_artist.presentation.screen.ArtistChooserScreen

@Composable
fun ArtistChooserRoute(
    selectedAction: AppBottomBarAction,
    artistStr: String,
    onArtistClick: (String) -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit,
    onBackClick: () -> Unit
) {
    ArtistChooserScreen(
        selectedAction = selectedAction,
        artistStr = artistStr,
        onArtistClick = onArtistClick,
        onBackClick = onBackClick,
        onBottomActionClick = onBottomActionClick
    )
}