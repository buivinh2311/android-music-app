package com.example.musicapplication.navigation.route

import androidx.compose.runtime.Composable
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_settings.presentation.SettingsScreen

@Composable
fun SettingsRoute(
    selectedAction: AppBottomBarAction,
    onSearchClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    SettingsScreen(
        selectedAction = selectedAction,
        onSearchClick = onSearchClick,
        onBottomActionClick = onBottomActionClick
    )
}