package com.example.musicapplication

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.core_model.settings.ThemeMode
import com.example.musicapplication.navigation.AppRoute

@Composable
fun SystemBarController(
    route: String?,
    themeMode: ThemeMode
) {
    val view = LocalView.current
    val isDark = when(themeMode) {
        ThemeMode.DARK -> true
        ThemeMode.LIGHT -> false
        ThemeMode.SYSTEM_DEFAULT -> isSystemInDarkTheme()
    }

    val lightStatusBar = when(route) {
        AppRoute.PLAYER, AppRoute.PLAYER_WITH_ARG,
        AppRoute.QUEUE -> false
        else -> !isDark
    }

    SideEffect {
        val window = (view.context as Activity).window
        val controller = WindowCompat.getInsetsController(window, view)
        controller.isAppearanceLightStatusBars = lightStatusBar
    }
}