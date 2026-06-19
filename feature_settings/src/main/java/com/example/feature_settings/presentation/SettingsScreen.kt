package com.example.feature_settings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_ui.component.AppBottomBar
import com.example.core_ui.component.AppTopBar
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_settings.presentation.components.PreferenceItem

@Composable
fun SettingsScreen(
    onSearchClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AppBottomBar(onBottomActionClick = onBottomActionClick)
        },
        topBar = {
            AppTopBar(
                title = stringResource(R.string.title_settings),
                onSearchClick = onSearchClick
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = AppDimens.Space.Lg)
        ) {
            Text(
                text = stringResource(R.string.title_theme),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )
            PreferenceItem(
                title = stringResource(R.string.text_system_default),
                summary = stringResource(R.string.text_system_default_summary)
            ) { }
            PreferenceItem(
                title = stringResource(R.string.text_light),
                summary = stringResource(R.string.text_light_summary)
            ) { }
            PreferenceItem(
                title = stringResource(R.string.text_dark),
                summary = stringResource(R.string.text_dark_summary)
            ) { }
        }
    }
}