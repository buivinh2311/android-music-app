package com.example.feature_settings.domain.repository

import com.example.core_resources.ui.theme.ThemeMode
import com.example.feature_settings.source.ThemeLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ThemeRepositoryImpl @Inject constructor(
    private val themeLocalDataSource: ThemeLocalDataSource
) : ThemeRepository {
    override fun getThemeMode(): Flow<ThemeMode> {
        return themeLocalDataSource.getThemeMode()
    }

    override suspend fun saveThemeMode(mode: ThemeMode) {
        themeLocalDataSource.saveThemeMode(mode)
    }
}