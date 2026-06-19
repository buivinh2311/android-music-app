package com.example.feature_settings.source

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.core_resources.ui.theme.ThemeMode
import com.example.feature_settings.DataStore.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ThemeLocalDataSourceImpl @Inject constructor(
    private val context: Context
): ThemeLocalDataSource {
    private val dataStore = context.dataStore
    private val darkThemeKey = stringPreferencesKey("dark_theme")

    override fun getThemeMode(): Flow<ThemeMode> {
        return context.dataStore.data.map { preferences ->
            ThemeMode.valueOf(
                preferences[darkThemeKey] ?: ThemeMode.SYSTEM_DEFAULT.name
            )
        }
    }

    override suspend fun saveThemeMode(mode: ThemeMode) {
        dataStore.edit { preferences ->
            preferences[darkThemeKey] = mode.name
        }
    }
}