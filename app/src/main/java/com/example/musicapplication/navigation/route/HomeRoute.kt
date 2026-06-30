package com.example.musicapplication.navigation.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.core_model.Song
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_home.presentation.HomeScreen
import com.example.musicapplication.navigation.AppRoute

@Composable
fun HomeRoute(
    navController: NavController,
    isConnect: Boolean,
    selectedAction: AppBottomBarAction,
    onSongOptionClick: (Song) -> Unit,
    onSearchClick: () -> Unit,
    onSongClick: (String) -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    HomeScreen(
        isConnect = isConnect,
        selectedAction = selectedAction,
        onSongOptionClick = onSongOptionClick,
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
        onBottomActionClick = onBottomActionClick
    )
}