package com.example.feature_artist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.Artist
import com.example.core_ui.state.UiState
import com.example.feature_artist.usecase.GetFavoriteArtistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowedArtistViewModel @Inject constructor(
    private val getFavoriteArtistUseCase: GetFavoriteArtistUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Artist>>>(
        UiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    init {
        observeFollowedArtist()
    }

    private fun observeFollowedArtist() {
        viewModelScope.launch {
            getFavoriteArtistUseCase().collect { artists ->
                _uiState.value = if (artists.isEmpty()) {
                    UiState.Empty
                } else {
                    UiState.Success(artists)
                }
            }
        }
    }
}