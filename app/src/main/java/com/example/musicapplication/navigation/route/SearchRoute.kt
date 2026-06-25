package com.example.musicapplication.navigation.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_search.presentation.SearchScreen
import com.example.musicapplication.navigation.AppRoute
import com.example.shared_presentation.menu.SongOptionItem

@Composable
fun SearchRoute(
    navController: NavController,
    onSongClick: (String) -> Unit,
    onBackClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit,
    onSongNavigationAction: (SongOptionItem) -> Unit
) {
    SearchScreen(
        onSongClick = onSongClick,
        onBackClick = onBackClick,
        onBottomActionClick = onBottomActionClick,
        onSongNavigationAction = onSongNavigationAction
    )
}