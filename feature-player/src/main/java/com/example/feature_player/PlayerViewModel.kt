package com.example.feature_player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_domain.usecase.FavoriteSongUseCases
import com.example.core_playback.MediaPlaybackController
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
    private val mediaPlaybackController: MediaPlaybackController,
    private val favoriteSongUseCases: FavoriteSongUseCases
): ViewModel() {

    private val _uiState = MutableStateFlow(PlayerUiState())
    val uiState: StateFlow<PlayerUiState> = _uiState

    fun pause() {
        mediaPlaybackController.pause()
    }

    fun resume() {
        mediaPlaybackController.resume()
    }

    fun seekTo(position: Long) {
        mediaPlaybackController.seekTo(position)
    }

    fun skipPrevious() {
        mediaPlaybackController.skipPrevious()
    }

    fun skipNext() {
        mediaPlaybackController.skipNext()
    }

    fun toggleShuffle() {
        mediaPlaybackController.toggleShuffle()
    }

    fun changeRepeatMode() {
        mediaPlaybackController.changeRepeatMode()
    }

    val playbackState = mediaPlaybackController.playbackState
    @OptIn(ExperimentalCoroutinesApi::class)
    val currentSongFavorite: StateFlow<Boolean> =
        playbackState
            .map { it.currentSongId }
            .filterNotNull()
            .distinctUntilChanged()
            .flatMapLatest { id ->
                favoriteSongUseCases.observeFavoriteSong(id)
            }
            .stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(), false)

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