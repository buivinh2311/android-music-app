package com.example.feature_album.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.Album
import com.example.core_ui.state.UiState
import com.example.core_utils.util.AppUtil
import com.example.feature_album.domain.usecase.GetTopAlbumsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val getTopAlbumsUseCase: GetTopAlbumsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Album>>>(
        UiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    init {
        loadAlbums()
    }

    private fun loadAlbums() {
        viewModelScope.launch {
            val albums = getTopAlbumsUseCase(AppUtil.DEFAULT_LIST_SIZE)
            _uiState.value = if(albums.isEmpty()) {
                UiState.Empty
            } else {
                UiState.Success(albums)
            }
        }
    }
}

