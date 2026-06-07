package com.example.feature_artist.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.Artist
import com.example.feature_artist.domain.usecase.GetArtistDetailUseCase
import com.example.feature_artist.presentation.state.ArtistChooserState
import com.example.feature_artist.presentation.state.ArtistDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistChooserViewModel @Inject constructor(
    private val getArtistDetailUseCase: GetArtistDetailUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(ArtistChooserState())
    val uiState: StateFlow<ArtistChooserState> = _uiState

    fun loadArtists(artistStr: String) {
        val artistNames = artistStr.trim().split("ft")
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            val artists = artistNames.map {
                getArtistDetailUseCase(it.trim()) ?:
                Artist(0, it.trim(), "", 0)
            }
            _uiState.update {
                it.copy(
                    artists = artists,
                    isLoading = false
                )
            }
        }
    }
}