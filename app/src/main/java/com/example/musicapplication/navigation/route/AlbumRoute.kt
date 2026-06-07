package com.example.musicapplication.navigation.route

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.core_ui.data.AppBottomBarAction
import com.example.feature_album.presentation.screen.AlbumScreen
import com.example.musicapplication.navigation.AppRoute

@Composable
fun AlbumRoute(
    navController: NavController,
    onBottomActionClick: (AppBottomBarAction) -> Unit,
    onBackClick: () -> Unit
) {
    AlbumScreen(
        onAlbumClick = { albumName ->
            navController.navigate("${AppRoute.ALBUM_DETAIL}/$albumName")
        },
        onBottomActionClick = onBottomActionClick,
        onBackClick = onBackClick
    )
}