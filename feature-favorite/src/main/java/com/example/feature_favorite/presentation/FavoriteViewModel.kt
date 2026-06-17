package com.example.feature_favorite.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_domain.usecase.FavoriteSongUseCases
import com.example.core_domain.usecase.PlaylistUseCases
import com.example.core_model.Song
import com.example.core_playback.PlaybackController
import com.example.core_playback.QueueSource
import com.example.feature_favorite.domain.usecase.GetAllFavoriteSongsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class FavoriteViewModel @Inject constructor(
    private val favoriteSongUseCases: FavoriteSongUseCases,
    private val playlistUseCases: PlaylistUseCases,
    getAllFavoriteSongsUseCase: GetAllFavoriteSongsUseCase,
    private val playbackController: PlaybackController
): ViewModel() {
    val songs = getAllFavoriteSongsUseCase()

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