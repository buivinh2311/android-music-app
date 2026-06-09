package com.example.feature_playlist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_playlist.domain.usecase.GetPlaylistByIdUseCase
import com.example.feature_playlist.domain.usecase.GetSongInPlaylistUseCase
import com.example.feature_playlist.presentation.state.PlaylistDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistDetailViewModel @Inject constructor(
    private val getPlaylistByIdUseCase: GetPlaylistByIdUseCase,
    private val getSongInPlaylistUseCase: GetSongInPlaylistUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(PlaylistDetailUiState())
    val uiState: StateFlow<PlaylistDetailUiState> = _uiState

    fun loadPlaylist(playlistId: Int) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            val playlist = getPlaylistByIdUseCase(playlistId)
            _uiState.update {
                it.copy(
                    playlist = playlist,
                    isLoading = false
                )
            }
        }
        loadSongs(playlistId)
    }

    fun loadSongs(playlistId: Int) {
        viewModelScope.launch {
            getSongInPlaylistUseCase(playlistId)
                .collect { songs ->
                    _uiState.update {
                        it.copy(
                            songs = songs,
                            isLoading = false
                        )
                    }
                }
        }
    }
}