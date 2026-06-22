package com.example.feature_settings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_model.ThemeMode
import com.example.core_ui.component.AppBottomBar
import com.example.core_ui.component.AppTopBar
import com.example.core_ui.menu.AppBottomBarAction
import com.example.core_ui.state.UiState
import com.example.feature_settings.presentation.component.PreferenceItem

@Composable
fun SettingsScreen(
    onSearchClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    val settingsViewModel: SettingsViewModel = hiltViewModel()
    val themeState by settingsViewModel.themeState.collectAsStateWithLifecycle()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AppBottomBar(onBottomActionClick = onBottomActionClick)
        },
        topBar = {
            AppTopBar(
                title = stringResource(R.string.title_home),
                onSearchClick = onSearchClick
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        when(val state = themeState) {
            is UiState.Success -> {
                val themeMode = state.data
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .padding(vertical = AppDimens.Space.Lg)
                ) {
                    Text(
                        text = stringResource(R.string.title_theme),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(horizontal = AppDimens.Space.Lg)
                    )
                    PreferenceItem(
                        title = stringResource(R.string.text_system_default),
                        summary = stringResource(R.string.text_system_default_summary),
                        selected = themeMode == ThemeMode.SYSTEM_DEFAULT,
                        onClick = {
                            settingsViewModel.toggleDarkMode(ThemeMode.SYSTEM_DEFAULT)
                        }
                    )
                    PreferenceItem(
                        title = stringResource(R.string.text_light),
                        summary = stringResource(R.string.text_light_summary),
                        selected = themeMode == ThemeMode.LIGHT,
                        onClick = {
                            settingsViewModel.toggleDarkMode(ThemeMode.LIGHT)
                        }
                    )
                    PreferenceItem(
                        title = stringResource(R.string.text_dark),
                        summary = stringResource(R.string.text_dark_summary),
                        selected = themeMode == ThemeMode.DARK,
                        onClick = {
                            settingsViewModel.toggleDarkMode(ThemeMode.DARK)
                        }
                    )
                }
            }

            else -> {}
        }
    }
}