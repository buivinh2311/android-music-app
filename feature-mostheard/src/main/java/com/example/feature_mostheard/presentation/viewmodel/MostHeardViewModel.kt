package com.example.feature_mostheard.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_utils.util.AppUtil
import com.example.feature_mostheard.domain.GetMostHeardSongsUseCase
import com.example.feature_mostheard.presentation.state.MostHeardUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MostHeardViewModel @Inject constructor(
    private val getMostHeardSongsUseCase: GetMostHeardSongsUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(MostHeardUiState())
    val uiState: StateFlow<MostHeardUiState> = _uiState

    init {
        loadMostHeardSongs()
    }

    private fun loadMostHeardSongs() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            val songs = getMostHeardSongsUseCase(AppUtil.DEFAULT_LIST_SIZE)
            _uiState.update {
                it.copy(
                    songs = songs,
                    isLoading = false
                )
            }
        }
    }
}