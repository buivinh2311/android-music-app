package com.example.musicapplication.navigation.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.core_ui.data.AppBottomBarAction
import com.example.core_ui.data.SongOptionItem
import com.example.feature_recommended.presentation.screen.RecommendedScreen
import com.example.musicapplication.navigation.AppRoute

@Composable
fun RecommendedRoute(
    navController: NavController,
    onBackClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit,
    onSongOptionClick: (SongOptionItem) -> Unit
) {
    RecommendedScreen(
        onSongClick = { songId ->
            navController.navigate("${AppRoute.PLAYER}/$songId")
        },
        onBackCLick = onBackClick,
        onBottomActionClick = onBottomActionClick,
        onSongOptionClick = onSongOptionClick
    )
}