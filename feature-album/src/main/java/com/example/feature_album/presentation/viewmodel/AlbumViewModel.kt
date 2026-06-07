package com.example.feature_album.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_utils.util.AppUtil
import com.example.feature_album.domain.usecase.GetTopAlbumsUseCase
import com.example.feature_album.presentation.state.AlbumUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val getTopAlbumsUseCase: GetTopAlbumsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(AlbumUiState())
    val uiState: StateFlow<AlbumUiState> = _uiState

    init {
        loadAlbums()
    }

    private fun loadAlbums() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            val albums = getTopAlbumsUseCase(AppUtil.DEFAULT_LIST_SIZE)
            _uiState.update {
                it.copy(
                    albums = albums,
                    isLoading = false,
                )
            }
        }
    }
}

