package com.example.infrastructure.repository

import com.example.core_domain.repository.ThemeRepository
import com.example.core_model.settings.ThemeMode
import com.example.infrastructure.source.theme.ThemePreferencesDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ThemeRepositoryImpl @Inject constructor(
    private val themePreferencesDataSource: ThemePreferencesDataSource
) : ThemeRepository {
    override fun getThemeMode(): Flow<ThemeMode> {
        return themePreferencesDataSource.getThemeMode()
    }

    override suspend fun saveThemeMode(mode: ThemeMode) {
        themePreferencesDataSource.saveThemeMode(mode)
    }
}