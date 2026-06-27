package com.example.feature_album.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.Album
import com.example.core_model.Song
import com.example.core_model.playback.QueueSource
import com.example.core_playback.MediaPlaybackController
import com.example.core_ui.state.UiState
import com.example.feature_album.usecase.AddAlbumToFavoriteUseCase
import com.example.feature_album.usecase.GetAlbumDetailUseCase
import com.example.feature_album.usecase.GetSongsInAlbumUseCase
import com.example.feature_album.usecase.ObserveFavoriteAlbumUseCase
import com.example.feature_album.usecase.RemoveAlbumFromFavoriteUseCase
import com.example.feature_album.presentation.state.AlbumDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumDetailViewModel @Inject constructor(
    private val getAlbumDetailUseCase: GetAlbumDetailUseCase,
    private val getSongsInAlbumUseCase: GetSongsInAlbumUseCase,
    private val addAlbumToFavoriteUseCase: AddAlbumToFavoriteUseCase,
    private val removeAlbumFromFavoriteUseCase: RemoveAlbumFromFavoriteUseCase,
    private val observeFavoriteAlbumUseCase: ObserveFavoriteAlbumUseCase,
    private val mediaPlaybackController: MediaPlaybackController
): ViewModel() {
    private val _uiState = MutableStateFlow(AlbumDetailUiState())
    val uiState: StateFlow<AlbumDetailUiState> = _uiState

    fun loadAlbumDetail(albumName: String) {
        viewModelScope.launch {
            val album = getAlbumDetailUseCase(albumName)
            _uiState.update {
                it.copy(
                    album = if (album == null) {
                        UiState.Success(
                            Album(0, albumName, "", 1)
                        )
                    } else {
                        UiState.Success(album)
                    }
                )
            }
        }
    }

    fun loadSongs(albumName: String) {
        viewModelScope.launch {
            val songs = getSongsInAlbumUseCase(albumName)
            _uiState.update {
                it.copy(
                    songs = if (songs.isEmpty()) {
                        UiState.Empty
                    } else {
                        UiState.Success(songs)
                    }
                )
            }
        }
    }
    fun isFavoriteAlbum(albumName: String) = observeFavoriteAlbumUseCase(albumName)

    fun addAlbumToFavorite(albumName: String) {
        viewModelScope.launch {
            addAlbumToFavoriteUseCase(albumName)
        }
    }

    fun removeAlbumFromFavorite(albumName: String) {
        viewModelScope.launch {
            removeAlbumFromFavoriteUseCase(albumName)
        }
    }

    fun play(queueSource: QueueSource, queue: List<Song>, startSong: Song) {
        mediaPlaybackController.play(queueSource, queue, startSong)
    }
}