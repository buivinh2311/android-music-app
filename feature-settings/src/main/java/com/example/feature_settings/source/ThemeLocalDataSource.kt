package com.example.feature_settings.source

import com.example.core_resources.ui.theme.ThemeMode
import kotlinx.coroutines.flow.Flow

interface ThemeLocalDataSource {
    fun getThemeMode(): Flow<ThemeMode>
    suspend fun saveThemeMode(mode: ThemeMode)
}