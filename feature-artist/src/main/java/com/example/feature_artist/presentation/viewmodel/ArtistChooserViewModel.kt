package com.example.feature_artist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.Artist
import com.example.core_ui.state.UiState
import com.example.feature_artist.domain.usecase.GetArtistDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistChooserViewModel @Inject constructor(
    private val getArtistDetailUseCase: GetArtistDetailUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Artist>>>(
        UiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    fun loadArtists(artistStr: String) {
        val artistNames = artistStr.trim().split("ft")
        viewModelScope.launch {
            val artists = artistNames.map {
                getArtistDetailUseCase(it.trim()) ?:
                Artist(0, it.trim(), "", 0)
            }
            _uiState.value = if(artists.isEmpty()) {
                UiState.Empty
            } else {
                UiState.Success(artists)
            }
        }
    }
}