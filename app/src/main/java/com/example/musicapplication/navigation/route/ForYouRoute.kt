package com.example.musicapplication.navigation.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_foryou.presentation.ForYouScreen
import com.example.musicapplication.navigation.AppRoute
import com.example.shared_presentation.menu.SongOptionItem

@Composable
fun ForYouRoute(
    navController: NavController,
    onBackClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit,
    onSongNavigationAction: (SongOptionItem) -> Unit
) {
    ForYouScreen(
        onSongClick = { songId ->
            navController.navigate("${AppRoute.PLAYER}/$songId")
        },
        onBackCLick = onBackClick,
        onBottomActionClick = onBottomActionClick,
        onSongNavigationAction = onSongNavigationAction
    )
}