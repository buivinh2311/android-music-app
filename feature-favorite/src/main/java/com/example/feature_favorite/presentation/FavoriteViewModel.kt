package com.example.feature_favorite.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_domain.usecase.FavoriteSongUseCases
import com.example.core_domain.usecase.PlaylistUseCases
import com.example.core_model.Song
import com.example.core_playback.MediaPlaybackController
import com.example.core_model.playback.QueueSource
import com.example.core_ui.state.UiState
import com.example.feature_favorite.domain.usecase.GetAllFavoriteSongsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteSongUseCases: FavoriteSongUseCases,
    private val playlistUseCases: PlaylistUseCases,
    private val getAllFavoriteSongsUseCase: GetAllFavoriteSongsUseCase,
    private val mediaPlaybackController: MediaPlaybackController
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Song>>>(
        UiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    init {
        observeFavoriteSongs()
    }

    private fun observeFavoriteSongs() {
        viewModelScope.launch {
            getAllFavoriteSongsUseCase().collect { songs ->
                _uiState.value = if (songs.isEmpty()) {
                    UiState.Empty
                } else {
                    UiState.Success(songs)
                }
            }
        }
    }

    val playlists = playlistUseCases.getAllPlaylist()

    fun play(queueSource: QueueSource, queue: List<Song>, startSong: Song) {
        mediaPlaybackController.play(queueSource, queue, startSong)
    }
}