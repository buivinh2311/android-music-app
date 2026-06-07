package com.example.feature_home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_utils.util.AppUtil
import com.example.feature_home.domain.usecase.GetRecommendedSongsUseCase
import com.example.feature_home.domain.usecase.GetTopAlbumUseCas
import com.example.feature_home.presentation.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTopAlbumUseCas: GetTopAlbumUseCas,
    private val getRecommendedSongsUseCase: GetRecommendedSongsUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState

    init {
        loadHotAlbums()
        loadRecommendedSongs()
    }

    fun loadHotAlbums() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            val albums = getTopAlbumUseCas(AppUtil.SECTION_PAGE_SIZE)
            _uiState.update {
                it.copy(
                    hotAlbums = albums,
                    isLoading = false
                )
            }
        }
    }

    fun loadRecommendedSongs() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            val songs = getRecommendedSongsUseCase(AppUtil.SECTION_PAGE_SIZE)
            _uiState.update {
                it.copy(
                    recommendedSongs = songs,
                    isLoading = false
                )
            }
        }
    }
}