package com.example.feature_artist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_domain.usecase.FavoriteSongUseCases
import com.example.core_model.Artist
import com.example.core_playback.PlaybackController
import com.example.feature_artist.domain.usecase.GetFavoriteArtistUseCase
import com.example.feature_artist.domain.usecase.ObserverFavoriteArtistUseCase
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowedArtistViewModel @Inject constructor(
    getFavoriteArtistUseCase: GetFavoriteArtistUseCase,
    private val favoriteSongUseCases: FavoriteSongUseCases,
    private val playbackController: PlaybackController
): ViewModel() {
    val followedArtists = getFavoriteArtistUseCase()

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