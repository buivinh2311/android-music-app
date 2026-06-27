package com.example.musicapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_domain.usecase.DownloadSongUseCases
import com.example.core_domain.usecase.FavoriteSongUseCases
import com.example.core_domain.usecase.PlaylistUseCases
import com.example.core_model.Song
import com.example.core_model.settings.ThemeMode
import com.example.core_network.NetworkMonitor
import com.example.core_playback.MediaPlaybackController
import com.example.core_ui.state.UiState
import com.example.musicapplication.usecase.CleanUpUseCase
import com.example.musicapplication.usecase.GetThemeModeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val cleanUpUseCase: CleanUpUseCase,
    private val favoriteSongUseCases: FavoriteSongUseCases,
    private val playlistUseCases: PlaylistUseCases,
    private val mediaPlaybackController: MediaPlaybackController,
    private val getThemeModeUseCase: GetThemeModeUseCase,
    private val downloadUseCases: DownloadSongUseCases,
    networkMonitor: NetworkMonitor
) : ViewModel() {
    private val _themeState = MutableStateFlow<UiState<ThemeMode>>(
        UiState.Loading
    )
    val themeState = _themeState.asStateFlow()

    init {
        viewModelScope.launch {
            cleanUpUseCase()
        }
        observeThemeMode()
    }

    private fun observeThemeMode() {
        viewModelScope.launch {
            getThemeModeUseCase().collect { themeMode ->
                _themeState.value = UiState.Success(themeMode)
            }
        }
    }

    val playbackState = mediaPlaybackController.playbackState
    val isConnect = networkMonitor.isConnect

    @OptIn(ExperimentalCoroutinesApi::class)
    val currentFavoriteSong: StateFlow<Boolean> =
        playbackState
            .map { it.currentSongId }
            .filterNotNull()
            .distinctUntilChanged()
            .flatMapLatest { id ->
                favoriteSongUseCases.observeFavoriteSong(id)
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    val playlists = playlistUseCases.getAllPlaylist()
    fun isDownloadSong(songId: String) = downloadUseCases.observeDownloadSong(songId)
    fun isFavoriteSong(songId: String) = favoriteSongUseCases.observeFavoriteSong(songId)

    fun createPlaylist(playlistName: String) {
        viewModelScope.launch {
            playlistUseCases.createPlaylist(playlistName)
        }
    }

    fun addSongToPlaylist(playlistId: Int, songId: String) {
        viewModelScope.launch {
            playlistUseCases.addSongToPlaylist(playlistId, songId)
        }
    }

    fun addSongToFavorite(songId: String) {
        viewModelScope.launch {
            favoriteSongUseCases.addSongToFavorite(songId)
        }
    }

    fun removeSongFromFavorite(songId: String) {
        viewModelScope.launch {
            favoriteSongUseCases.removeSongFromFavorite(songId)
        }
    }

    fun pause() {
        mediaPlaybackController.pause()
    }

    fun resume() {
        mediaPlaybackController.resume()
    }

    fun skipNext() {
        mediaPlaybackController.skipNext()
    }

    fun download(song: Song) {
        viewModelScope.launch {
            downloadUseCases.addSongToDownload(song)
        }
    }

    fun removeDownloadSong(songId: String) {
        viewModelScope.launch {
            downloadUseCases.removeSongFromDownload(songId)
        }
    }
}