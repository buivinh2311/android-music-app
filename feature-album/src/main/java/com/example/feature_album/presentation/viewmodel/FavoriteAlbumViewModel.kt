package com.example.feature_album.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_domain.usecase.FavoriteSongUseCases
import com.example.core_model.Album
import com.example.core_playback.MediaPlaybackController
import com.example.core_ui.state.UiState
import com.example.feature_album.domain.usecase.GetFavoriteAlbumUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteAlbumViewModel @Inject constructor(
    private val getFavoriteAlbumUseCase: GetFavoriteAlbumUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Album>>>(
        UiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    init {
        observeFavoriteAlbum()
    }

    private fun observeFavoriteAlbum() {
        viewModelScope.launch {
            getFavoriteAlbumUseCase().collect { albums ->
                _uiState.value = if (albums.isEmpty()) {
                    UiState.Empty
                } else {
                    UiState.Success(albums)
                }
            }
        }
    }
}