package com.example.feature_player.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_domain.usecase.FavoriteSongUseCases
import com.example.core_domain.usecase.PlaylistUseCases
import com.example.core_playback.PlaybackController
import com.example.feature_player.domain.usecase.GetSongByIdUseCase
import com.example.feature_player.presentation.state.PlayerUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val playbackController: PlaybackController,
    private val favoriteSongUseCases: FavoriteSongUseCases,
    private val playlistUseCases: PlaylistUseCases,
    private val getSongByIdUseCase: GetSongByIdUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(PlayerUiState())
    val uiState: StateFlow<PlayerUiState> = _uiState

    fun loadSong(songId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true
            )
            val song = getSongByIdUseCase(songId)
            _uiState.value = _uiState.value.copy(
                song = song,
                isLoading = false
            )
        }
    }

    fun pause() {
        playbackController.pause()
    }

    fun resume() {
        playbackController.resume()
    }

    fun seekTo(position: Long) {
        playbackController.seekTo(position)
    }

    fun skipPrevious() {
        playbackController.skipPrevious()
    }

    fun skipNext() {
        playbackController.skipNext()
    }

    fun toggleShuffle() {
        playbackController.toggleShuffle()
    }

    fun changeRepeatMode() {
        playbackController.changeRepeatMode()
    }

    val playbackState = playbackController.playbackState
    val playlists = playlistUseCases.getAllPlaylist()
    @OptIn(ExperimentalCoroutinesApi::class)
    val currentSongFavorite: StateFlow<Boolean> =
        playbackState
            .map { it.currentSongId }
            .filterNotNull()
            .distinctUntilChanged()
            .flatMapLatest { id ->
                favoriteSongUseCases.observerFavoriteSong(id)
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)
    fun isFavoriteSong(songId: String) = favoriteSongUseCases.observerFavoriteSong(songId)

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

    fun removeSongToFavorite(songId: String) {
        viewModelScope.launch {
            favoriteSongUseCases.removeSongFromFavorite(songId)
        }
    }
}