package com.example.infrastructure.source.theme

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.core_model.settings.ThemeMode
import com.example.infrastructure.source.theme.datastore.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ThemePreferencesDataSourceImpl @Inject constructor(
    private val context: Context
): ThemePreferencesDataSource {
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