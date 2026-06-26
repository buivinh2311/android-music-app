package com.example.musicapplication

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_resources.ui.theme.MusicApplicationTheme
import com.example.core_ui.component.EmptyScreen
import com.example.core_ui.component.NetworkBanner
import com.example.core_ui.component.showToast
import com.example.core_ui.state.UiState
import com.example.core_utils.util.AppUtil
import com.example.feature_settings.presentation.SettingsViewModel
import com.example.musicapplication.navigation.AppNavHost
import com.example.shared_presentation.presentation.MiniPlayer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var keepSplash = true

    @SuppressLint("LocalContextGetResourceValueCall")
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
            val playbackState by mainViewModel.playbackState
                .collectAsStateWithLifecycle()

            val isCurrentFavoriteSong by mainViewModel.currentFavoriteSong
                .collectAsStateWithLifecycle()

            val isConnect by mainViewModel.isConnect
                .collectAsStateWithLifecycle()

            val currentSong = playbackState.queue.getOrNull(playbackState.currentIndex)
            val context = LocalContext.current

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
                            AppNavHost(
                                currentSong = currentSong,
                                isFavoriteSong = isCurrentFavoriteSong,
                                isConnect = isConnect,
                                isPlaying = playbackState.isPlaying,
                                onFavoriteClick = { song ->
                                    if (isCurrentFavoriteSong) {
                                        mainViewModel.removeSongFromFavorite(song.id)
                                        showToast(
                                            context,
                                            message = context.getString(
                                                R.string.remove_song_from_favorite_success,
                                                song.title
                                            )
                                        )
                                    } else {
                                        mainViewModel.addSongToFavorite(song.id)
                                        showToast(
                                            context,
                                            message = context.getString(
                                                R.string.add_song_to_favorite_success,
                                                song.title
                                            )
                                        )
                                    }
                                },
                                onTogglePlayClick = {
                                    if (playbackState.isPlaying) {
                                        mainViewModel.pause()
                                    } else {
                                        mainViewModel.resume()
                                    }
                                },
                                onNextClick = {
                                    mainViewModel.skipNext()
                                }
                            )
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