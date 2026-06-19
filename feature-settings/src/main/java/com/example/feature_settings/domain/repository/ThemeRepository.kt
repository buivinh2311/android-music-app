package com.example.feature_settings.domain.repository

import com.example.core_resources.ui.theme.ThemeMode
import kotlinx.coroutines.flow.Flow

interface ThemeRepository {
    fun getThemeMode(): Flow<ThemeMode>
    suspend fun saveThemeMode(mode: ThemeMode)
}