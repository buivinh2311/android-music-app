package com.example.feature_playlist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_domain.usecase.FavoriteSongUseCases
import com.example.core_domain.usecase.PlaylistUseCases
import com.example.core_model.Playlist
import com.example.core_model.Song
import com.example.core_playback.MediaPlaybackController
import com.example.core_model.QueueSource
import com.example.core_ui.state.UiState
import com.example.feature_playlist.domain.usecase.GetPlaylistByIdUseCase
import com.example.feature_playlist.domain.usecase.GetSongInPlaylistUseCase
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
class PlaylistDetailViewModel @Inject constructor(
    private val favoriteSongUseCases: FavoriteSongUseCases,
    private val playlistUseCases: PlaylistUseCases,
    private val getPlaylistByIdUseCase: GetPlaylistByIdUseCase,
    private val getSongInPlaylistUseCase: GetSongInPlaylistUseCase,
    private val mediaPlaybackController: MediaPlaybackController
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<Playlist>>(
        UiState.Loading
    )
    val uiState = _uiState.asStateFlow()
    fun songInPlaylist(playlistId: Int) = getSongInPlaylistUseCase(playlistId)

    fun loadPlaylist(playlistId: Int) {
        viewModelScope.launch {
            val playlist = getPlaylistByIdUseCase(playlistId)
            _uiState.value = if(playlist == null) {
                UiState.Empty
            } else {
                UiState.Success(playlist)
            }
        }
    }

    val playlists = playlistUseCases.getAllPlaylist()
    fun isFavoriteSong(songId: String) = favoriteSongUseCases.observerFavoriteSong(songId)

    fun createPlaylist(playlistName: String) {
        viewModelScope.launch {
            playlistUseCases.createPlaylist(playlistName)
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

    fun play(
        queueSource: QueueSource,
        queue: List<Song>,
        startSong: Song,
        playlistId: Int,
        playlistName: String
    ) {
        mediaPlaybackController.play(queueSource, queue, startSong, playlistId, playlistName)
    }
}