package com.example.feature_artist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.Artist
import com.example.core_ui.state.UiState
import com.example.core_utils.util.AppUtil
import com.example.feature_artist.usecase.GetTopArtistsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(
    private val getTopArtistsUseCase: GetTopArtistsUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Artist>>>(
        UiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    init {
        loadArtists()
    }

    private fun loadArtists() {
        viewModelScope.launch {
            val artists = getTopArtistsUseCase(AppUtil.DEFAULT_LIST_SIZE)
            _uiState.value = if(artists.isEmpty()) {
                UiState.Empty
            } else {
                UiState.Success(artists)
            }
        }
    }
}