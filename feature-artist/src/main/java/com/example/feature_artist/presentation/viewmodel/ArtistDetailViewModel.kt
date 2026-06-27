package com.example.feature_artist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.Artist
import com.example.core_model.Song
import com.example.core_model.playback.QueueSource
import com.example.core_playback.MediaPlaybackController
import com.example.core_ui.state.UiState
import com.example.feature_artist.usecase.AddArtistToFavoriteUseCase
import com.example.feature_artist.usecase.GetArtistDetailUseCase
import com.example.feature_artist.usecase.GetSongsForArtistUseCase
import com.example.feature_artist.usecase.ObserveFavoriteArtistUseCase
import com.example.feature_artist.usecase.RemoveArtistFromFavoriteUseCase
import com.example.feature_artist.presentation.state.ArtistDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistDetailViewModel @Inject constructor(
    private val getArtistDetailUseCase: GetArtistDetailUseCase,
    private val addArtistToFavoriteUseCase: AddArtistToFavoriteUseCase,
    private val removeArtistFromFavoriteUseCase: RemoveArtistFromFavoriteUseCase,
    private val observeFavoriteArtistUseCase: ObserveFavoriteArtistUseCase,
    private val getSongsForArtistUseCase: GetSongsForArtistUseCase,
    private val mediaPlaybackController: MediaPlaybackController
): ViewModel() {
    private val _uiState = MutableStateFlow(ArtistDetailState())
    val uiState: StateFlow<ArtistDetailState> = _uiState

    fun loadArtistDetail(artistName: String) {
        viewModelScope.launch {
            val artist = getArtistDetailUseCase(artistName)
            _uiState.update {
                it.copy(
                    artist = if(artist == null) {
                        UiState.Success(Artist(0, artistName, "", 0))
                    } else {
                        UiState.Success(artist)
                    }
                )
            }
        }
    }

    fun loadSongs(artistName: String) {
        viewModelScope.launch {
            val songs = getSongsForArtistUseCase(artistName)
            _uiState.update {
                it.copy(
                    songs = if(songs.isEmpty()) {
                        UiState.Empty
                    } else {
                        UiState.Success(songs)
                    }
                )
            }
        }
    }

    fun isFavoriteArtist(artistName: String) = observeFavoriteArtistUseCase(artistName)

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

    fun play(queueSource: QueueSource, queue: List<Song>, startSong: Song) {
        mediaPlaybackController.play(queueSource, queue, startSong)
    }
}