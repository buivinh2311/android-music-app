package com.example.feature_settings.domain.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.core_resources.ui.theme.ThemeMode
import com.example.feature_settings.utils.dataStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ThemeRepositoryImpl @Inject constructor(
    private val context: Context
): ThemeRepository {
    private val dataStore = context.dataStore
    private val darkThemeKey = stringPreferencesKey("dark_theme")

    override suspend fun getDarkTheme(): ThemeMode {
        val preferences = dataStore.data.first()
        return preferences[darkThemeKey]?.let { ThemeMode.valueOf(it) } ?: ThemeMode.SYSTEM_DEFAULT
    }

    override suspend fun saveDarkTheme(mode: ThemeMode) {
        dataStore.edit { preferences ->
            preferences[darkThemeKey] = mode.name
        }
    }
}