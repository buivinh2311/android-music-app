package com.example.feature_settings.domain.repository

import com.example.core_resources.ui.theme.ThemeMode

interface ThemeRepository {
    suspend fun getDarkTheme(): ThemeMode
    suspend fun saveDarkTheme(mode: ThemeMode)
}