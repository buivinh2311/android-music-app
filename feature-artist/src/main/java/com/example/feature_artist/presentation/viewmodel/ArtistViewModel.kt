package com.example.feature_artist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_utils.util.AppUtil
import com.example.feature_artist.domain.usecase.GetTopArtistsUseCase
import com.example.feature_artist.presentation.state.ArtistState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(
    private val getTopArtistsUseCase: GetTopArtistsUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(ArtistState())
    val uiState: StateFlow<ArtistState> = _uiState

    init {
        loadArtists()
    }

    private fun loadArtists() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            val artists = getTopArtistsUseCase(AppUtil.DEFAULT_LIST_SIZE)
            _uiState.update {
                it.copy(
                    artists = artists,
                    isLoading = false
                )
            }
        }
    }
}