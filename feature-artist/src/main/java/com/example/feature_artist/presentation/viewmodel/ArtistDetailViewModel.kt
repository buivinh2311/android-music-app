package com.example.feature_artist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_artist.domain.usecase.GetArtistDetailUseCase
import com.example.feature_artist.domain.usecase.GetSongsForArtistUseCase
import com.example.feature_artist.presentation.state.ArtistDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistDetailViewModel @Inject constructor(
    private val getArtistDetailUseCase: GetArtistDetailUseCase,
    private val getSongsForArtistUseCase: GetSongsForArtistUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(ArtistDetailState())
    val uiState: StateFlow<ArtistDetailState> = _uiState

    fun loadArtistDetail(artistName: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            val artist = getArtistDetailUseCase(artistName)
            _uiState.update {
                it.copy(
                    artist = artist,
                    isLoading = false
                )
            }
            loadSongs(artistName)
        }
    }

    fun loadSongs(artistName: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            val songs = getSongsForArtistUseCase(artistName)
            _uiState.update {
                it.copy(
                    songs = songs,
                    isLoading = false
                )
            }
        }
    }
}