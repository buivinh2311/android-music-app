package com.example.feature_settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_resources.ui.theme.ThemeMode
import com.example.feature_settings.domain.repository.ThemeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ThemeViewModel @Inject constructor(
    private val themeRepository: ThemeRepository
) : ViewModel() {
    private val _darkMode = MutableStateFlow(ThemeMode.SYSTEM_DEFAULT)
    val darkMode: StateFlow<ThemeMode> = _darkMode

    init {
        viewModelScope.launch {
            _darkMode.value = themeRepository.getDarkTheme()
        }
    }

    fun toggleDarkMode(mode: ThemeMode) {
        _darkMode.value = mode
        viewModelScope.launch {
            themeRepository.saveDarkTheme(mode)
        }
    }
}