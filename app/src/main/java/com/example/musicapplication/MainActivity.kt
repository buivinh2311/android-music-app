package com.example.musicapplication

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_resources.ui.theme.MusicApplicationTheme
import com.example.core_ui.component.EmptyScreen
import com.example.core_ui.state.UiState
import com.example.feature_settings.presentation.SettingsViewModel
import com.example.musicapplication.navigation.AppNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var keepSplash = true

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            keepSplash
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                100
            )
        }

        setContent {
            val mainViewModel: MainViewModel = hiltViewModel()
            val settingsViewModel: SettingsViewModel = hiltViewModel()
            val themeState by settingsViewModel.themeState.collectAsStateWithLifecycle()
            when(val state = themeState) {
                is UiState.Success -> {
                    keepSplash = false
                    val themeMode = state.data
                    MusicApplicationTheme(
                        themeMode = themeMode
                    ) {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            AppNavHost()
                        }
                    }
                }

                else -> {
                    EmptyScreen()
                }
            }
        }
    }
}