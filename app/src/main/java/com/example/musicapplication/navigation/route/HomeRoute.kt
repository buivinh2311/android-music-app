package com.example.musicapplication.navigation.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.core_ui.data.AppBottomBarAction
import com.example.core_ui.data.SongOptionItem
import com.example.feature_home.presentation.screen.HomeScreen
import com.example.musicapplication.navigation.AppRoute

@Composable
fun HomeRoute(
    navController: NavController,
    onSearchClick: () -> Unit,
    onSongClick: (String) -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit,
    onSongOptionClick: (SongOptionItem) -> Unit
) {
    HomeScreen(
        onMoreAlbumClick = {
            navController.navigate(AppRoute.ALBUM)
        },

        onAlbumClick = { albumName ->
            navController.navigate("${AppRoute.ALBUM_DETAIL}/$albumName")
        },

        onRecommendedClick = {
            navController.navigate(AppRoute.RECOMMENDED)
        },

        onSearchClick = onSearchClick,
        onSongClick = onSongClick,
        onBottomActionClick = onBottomActionClick,
        onSongOptionClick = onSongOptionClick
    )
}