package com.example.musicapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_artist.presentation.screen.FollowedArtistScreen

@Composable
fun FollowedArtistRoute(
    selectedAction: AppBottomBarAction,
    onArtistClick: (String) -> Unit,
    onBackClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    FollowedArtistScreen(
        selectedAction = selectedAction,
        onArtistClick = onArtistClick,
        onBackClick = onBackClick,
        onBottomActionClick = onBottomActionClick
    )
}