package com.example.feature_artist.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_domain.usecase.FavoriteSongUseCases
import com.example.core_domain.usecase.PlaylistUseCases
import com.example.core_model.Playlist
import com.example.feature_artist.domain.usecase.AddArtistToFavoriteUseCase
import com.example.feature_artist.domain.usecase.GetArtistDetailUseCase
import com.example.feature_artist.domain.usecase.GetSongsForArtistUseCase
import com.example.feature_artist.domain.usecase.ObserverFavoriteArtistUseCase
import com.example.feature_artist.domain.usecase.RemoveArtistFromFavoriteUseCase
import com.example.feature_artist.presentation.state.ArtistDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
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
    private val observerFavoriteArtistUseCase: ObserverFavoriteArtistUseCase,
    private val getSongsForArtistUseCase: GetSongsForArtistUseCase
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

    fun isFavoriteArtist(artistName: String) = observerFavoriteArtistUseCase(artistName)
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

    fun removeSongToFavorite(songId: String) {
        viewModelScope.launch {
            favoriteSongUseCases.removeSongFromFavorite(songId)
        }
    }
}