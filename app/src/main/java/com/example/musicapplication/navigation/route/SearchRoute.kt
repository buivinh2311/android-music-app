package com.example.musicapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.core_model.Song
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_search.presentation.SearchScreen

@Composable
fun SearchRoute(
    isConnect: Boolean,
    selectedAction: AppBottomBarAction,
    onSongOptionClick: (Song) -> Unit,
    onSongClick: (String) -> Unit,
    onBackClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    SearchScreen(
        isConnect = isConnect,
        selectedAction = selectedAction,
        onSongOptionClick = onSongOptionClick,
        onSongClick = onSongClick,
        onBackClick = onBackClick,
        onBottomActionClick = onBottomActionClick
    )
}