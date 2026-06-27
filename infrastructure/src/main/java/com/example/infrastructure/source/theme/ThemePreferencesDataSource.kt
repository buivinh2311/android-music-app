package com.example.infrastructure.source.theme

import com.example.core_model.settings.ThemeMode
import kotlinx.coroutines.flow.Flow

interface ThemePreferencesDataSource {
    fun getThemeMode(): Flow<ThemeMode>
    suspend fun saveThemeMode(mode: ThemeMode)
}