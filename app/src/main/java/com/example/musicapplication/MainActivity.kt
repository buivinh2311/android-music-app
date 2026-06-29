package com.example.musicapplication

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_resources.R
import com.example.core_resources.ui.theme.MusicApplicationTheme
import com.example.core_ui.component.EmptyScreen
import com.example.core_ui.component.showToast
import com.example.core_ui.state.UiState
import com.example.musicapplication.navigation.AppNavHost
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
            val themeState by mainViewModel.themeState.collectAsStateWithLifecycle()

            val playlists by mainViewModel.playlists
                .collectAsStateWithLifecycle(emptyList())

            val playbackState by mainViewModel.playbackState
                .collectAsStateWithLifecycle()

            val isCurrentFavoriteSong by mainViewModel.currentFavoriteSong
                .collectAsStateWithLifecycle()

            val isConnect by mainViewModel.isConnect
                .collectAsStateWithLifecycle()

            val currentSong = playbackState.playQueue.getOrNull(playbackState.currentIndex)
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
                                themeMode = themeMode,
                                currentSong = currentSong,
                                isFavoriteSong = isCurrentFavoriteSong,
                                isConnect = isConnect,
                                isPlaying = playbackState.isPlaying,
                                playlists = playlists,
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
                                observeFavoriteSong = { songId ->
                                    mainViewModel.isFavoriteSong(songId)
                                },
                                observeDownloadSong = { songId ->
                                    mainViewModel.isDownloadSong(songId)
                                },
                                onAddSongToDownload = { song ->
                                    mainViewModel.download(song)
                                },
                                onRemoveSongFromDownload = { songId ->
                                    mainViewModel.removeDownloadSong(songId)
                                },
                                onAddSongToFavorite = { songId ->
                                    mainViewModel.addSongToFavorite(songId)
                                },
                                onRemoveSongFromFavorite = { songId ->
                                    mainViewModel.removeSongFromFavorite(songId)
                                },
                                onCreatePlaylist = { playlistName ->
                                    mainViewModel.createPlaylist(playlistName)
                                },
                                onAddSongToPlaylist = { playlistId, songId ->
                                    mainViewModel.addSongToPlaylist(playlistId, songId)
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