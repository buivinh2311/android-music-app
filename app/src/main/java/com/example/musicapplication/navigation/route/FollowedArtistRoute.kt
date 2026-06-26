package com.example.musicapplication.navigation.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_artist.presentation.screen.FollowedArtistScreen
import com.example.musicapplication.navigation.AppRoute

@Composable
fun FollowedArtistRoute(
    onArtistClick: (String) -> Unit,
    onBackClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    FollowedArtistScreen(
        onArtistClick = onArtistClick,
        onBackClick = onBackClick,
        onBottomActionClick = onBottomActionClick
    )
}