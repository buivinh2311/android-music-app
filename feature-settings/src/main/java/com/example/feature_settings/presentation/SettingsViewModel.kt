package com.example.feature_settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.settings.ThemeMode
import com.example.core_ui.state.UiState
import com.example.feature_settings.usecase.GetThemeModeUseCase
import com.example.feature_settings.usecase.SaveThemeModeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getThemeModeUseCase: GetThemeModeUseCase,
    private val saveThemeModeUseCase: SaveThemeModeUseCase
) : ViewModel() {

    private val _themeState = MutableStateFlow<UiState<ThemeMode>>(
        UiState.Loading
    )
    val themeState = _themeState.asStateFlow()

    init {
        observeThemeMode()
    }

    private fun observeThemeMode() {
        viewModelScope.launch {
            getThemeModeUseCase().collect { themeMode ->
                _themeState.value = UiState.Success(themeMode)
            }
        }
    }

    fun toggleDarkMode(mode: ThemeMode) {
        viewModelScope.launch {
            saveThemeModeUseCase(mode)
        }
    }
}