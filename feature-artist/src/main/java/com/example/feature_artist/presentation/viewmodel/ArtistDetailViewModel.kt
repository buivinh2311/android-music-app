package com.example.feature_artist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_domain.usecase.FavoriteSongUseCases
import com.example.core_domain.usecase.PlaylistUseCases
import com.example.core_model.Song
import com.example.core_playback.PlaybackController
import com.example.core_playback.QueueSource
import com.example.feature_artist.domain.usecase.AddArtistToFavoriteUseCase
import com.example.feature_artist.domain.usecase.GetArtistDetailUseCase
import com.example.feature_artist.domain.usecase.GetSongsForArtistUseCase
import com.example.feature_artist.domain.usecase.ObserveFavoriteArtistUseCase
import com.example.feature_artist.domain.usecase.RemoveArtistFromFavoriteUseCase
import com.example.feature_artist.presentation.state.ArtistDetailState
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
class ArtistDetailViewModel @Inject constructor(
    private val favoriteSongUseCases: FavoriteSongUseCases,
    private val playlistUseCases: PlaylistUseCases,
    private val getArtistDetailUseCase: GetArtistDetailUseCase,
    private val addArtistToFavoriteUseCase: AddArtistToFavoriteUseCase,
    private val removeArtistFromFavoriteUseCase: RemoveArtistFromFavoriteUseCase,
    private val observeFavoriteArtistUseCase: ObserveFavoriteArtistUseCase,
    private val getSongsForArtistUseCase: GetSongsForArtistUseCase,
    private val playbackController: PlaybackController
): ViewModel() {
    private val _uiState = MutableStateFlow(ArtistDetailState())
    val uiState: StateFlow<ArtistDetailState> = _uiState

    fun loadArtistDetail(artistName: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            val artist = getArtistDetailUseCase(artistName)
            _uiState.update {
                it.copy(
                    artist = artist,
                    isLoading = false
                )
            }
            loadSongs(artistName)
        }
    }

    fun loadSongs(artistName: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            val songs = getSongsForArtistUseCase(artistName)
            _uiState.update {
                it.copy(
                    songs = songs,
                    isLoading = false
                )
            }
        }
    }

    val playbackState = playbackController.playbackState
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

    fun isFavoriteArtist(artistName: String) = observeFavoriteArtistUseCase(artistName)
    val playlists = playlistUseCases.getAllPlaylist()
    fun isFavoriteSong(songId: String) = favoriteSongUseCases.observerFavoriteSong(songId)

    fun addArtistToFavorite(artistName: String) {
        viewModelScope.launch {
            addArtistToFavoriteUseCase(artistName)
        }
    }

    fun removeArtistFromFavorite(artistName: String) {
        viewModelScope.launch {
            removeArtistFromFavoriteUseCase(artistName)
        }
    }

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

    fun play(queueSource: QueueSource, queue: List<Song>, startSong: Song) {
        playbackController.play(queueSource, queue, startSong)
    }

    fun pause() {
        playbackController.pause()
    }

    fun resume() {
        playbackController.resume()
    }

    fun skipNext() {
        playbackController.skipNext()
    }
}