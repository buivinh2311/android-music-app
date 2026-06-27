package com.example.feature_settings.usecase

import com.example.core_domain.repository.ThemeRepository
import com.example.core_model.settings.ThemeMode
import javax.inject.Inject

class SaveThemeModeUseCase @Inject constructor(
    private val repository: ThemeRepository
) {
    suspend operator fun invoke(themeMode: ThemeMode) {
        repository.saveThemeMode(themeMode)
    }
}