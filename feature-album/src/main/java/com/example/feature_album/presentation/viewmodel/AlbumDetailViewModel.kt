package com.example.feature_album.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_domain.usecase.FavoriteSongUseCases
import com.example.core_domain.usecase.PlaylistUseCases
import com.example.core_model.Album
import com.example.core_model.Song
import com.example.core_playback.MediaPlaybackController
import com.example.core_model.QueueSource
import com.example.core_ui.state.UiState
import com.example.feature_album.domain.usecase.AddAlbumToFavoriteUseCase
import com.example.feature_album.domain.usecase.GetAlbumDetailUseCase
import com.example.feature_album.domain.usecase.GetSongsInAlbumUseCase
import com.example.feature_album.domain.usecase.ObserveFavoriteAlbumUseCase
import com.example.feature_album.domain.usecase.RemoveAlbumFromFavoriteUseCase
import com.example.feature_album.presentation.state.AlbumDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumDetailViewModel @Inject constructor(
    private val favoriteSongUseCases: FavoriteSongUseCases,
    private val playlistUseCases: PlaylistUseCases,
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

    val playbackState = mediaPlaybackController.playbackState
    @OptIn(ExperimentalCoroutinesApi::class)
    val currentFavoriteSong: StateFlow<Boolean> =
        playbackState
            .map { it.currentSongId }
            .filterNotNull()
            .distinctUntilChanged()
            .flatMapLatest { id ->
                favoriteSongUseCases.observerFavoriteSong(id)
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)
    val playlists = playlistUseCases.getAllPlaylist()
    fun isFavoriteSong(songId: String) = favoriteSongUseCases.observerFavoriteSong(songId)
    fun isFavoriteAlbum(albumName: String) = observeFavoriteAlbumUseCase(albumName)

    fun createPlaylist(playlistName: String) {
        viewModelScope.launch {
            playlistUseCases.createPlaylist(playlistName)
        }
    }

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

    fun addSongToPlaylist(playlistId: Int, songId: String) {
        viewModelScope.launch {
            playlistUseCases.addSongToPlaylist(playlistId, songId)
        }
    }

    fun addSongToFavorite(songId: String) {
        viewModelScope.launch {
            favoriteSongUseCases.addSongToFavorite(songId)
        }
    }

    fun removeSongFromFavorite(songId: String) {
        viewModelScope.launch {
            favoriteSongUseCases.removeSongFromFavorite(songId)
        }
    }

    fun play(queueSource: QueueSource, queue: List<Song>, startSong: Song) {
        mediaPlaybackController.play(queueSource, queue, startSong)
    }

    fun pause() {
        mediaPlaybackController.pause()
    }

    fun resume() {
        mediaPlaybackController.resume()
    }

    fun skipNext() {
        mediaPlaybackController.skipNext()
    }
}