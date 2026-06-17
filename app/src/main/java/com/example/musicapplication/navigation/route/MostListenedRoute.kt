package com.example.musicapplication.navigation.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_mostheard.presentation.MostListenedScreen
import com.example.musicapplication.navigation.AppRoute
import com.example.shared_presentation.model.SongOptionItem

@Composable
fun MostListenedRoute(
    navController: NavController,
    onBackClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit,
    onSongNavigationAction: (SongOptionItem) -> Unit
) {
    MostListenedScreen(
        onSongClick = { songId ->
            navController.navigate("${AppRoute.PLAYER}/$songId")
        },
        onMiniPlayerClick = { songId ->
            navController.navigate("${AppRoute.PLAYER}/$songId")
        },
        onBackCLick = onBackClick,
        onBottomActionClick = onBottomActionClick,
        onSongNavigationAction = onSongNavigationAction
    )
}