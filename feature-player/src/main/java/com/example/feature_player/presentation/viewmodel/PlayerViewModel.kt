package com.example.feature_player.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.Song
import com.example.core_playback.PlaybackController
import com.example.feature_player.domain.usecase.GetDisplaySongByIdUseCase
import com.example.feature_player.domain.usecase.GetSongByIdUseCase
import com.example.feature_player.presentation.state.PlayerUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val playbackController: PlaybackController,
    private val getSongByIdUseCase: GetSongByIdUseCase,
    private val getDisplaySongByIdUseCase: GetDisplaySongByIdUseCase,
): ViewModel() {

    private val _uiState = MutableStateFlow(PlayerUiState())
    val uiState: StateFlow<PlayerUiState> = _uiState

    fun loadSong(songId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true
            )
            val song = getSongByIdUseCase(songId)
            val displaySong = getDisplaySongByIdUseCase(songId)
            _uiState.value = _uiState.value.copy(
                song = song,
                displaySong = displaySong,
                isLoading = false
            )
        }
    }

    fun play(song: Song) {
        playbackController.play(song)
    }

    fun pause() {
        playbackController.pause()
    }

    fun resume() {
        playbackController.resume()
    }
}