package com.example.feature_recommended.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_utils.util.AppUtil
import com.example.feature_recommended.domain.usecase.GetRecommendedSongUseCase
import com.example.feature_recommended.presentation.state.RecommendedUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecommendedViewModel @Inject constructor(
    private val getRecommendedSongUseCase: GetRecommendedSongUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(RecommendedUiState())
    val uiState: StateFlow<RecommendedUiState> = _uiState

    init {
        loadRecommendedSongs()
    }

    private fun loadRecommendedSongs() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            val songs = getRecommendedSongUseCase(AppUtil.DEFAULT_LIST_SIZE)
            _uiState.update {
                it.copy(
                    songs = songs,
                    isLoading = false
                )
            }
        }
    }
}