package com.example.feature_settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_resources.ui.theme.ThemeMode
import com.example.feature_settings.domain.repository.ThemeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val themeRepository: ThemeRepository
) : ViewModel() {
    val themeMode = themeRepository.getThemeMode()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = ThemeMode.SYSTEM_DEFAULT
            )

    fun toggleDarkMode(mode: ThemeMode) {
        viewModelScope.launch {
            themeRepository.saveThemeMode(mode)
        }
    }
}