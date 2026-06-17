package com.example.feature_foryou.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_domain.usecase.FavoriteSongUseCases
import com.example.core_domain.usecase.PlaylistUseCases
import com.example.core_model.Playlist
import com.example.core_model.Song
import com.example.core_playback.PlaybackController
import com.example.core_playback.QueueSource
import com.example.core_utils.util.AppUtil
import com.example.feature_foryou.domain.usecase.GetForYouSongsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
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
class ForYouViewModel @Inject constructor(
    private val favoriteSongUseCases: FavoriteSongUseCases,
    private val playlistUseCases: PlaylistUseCases,
    private val getForYouSongsUseCase: GetForYouSongsUseCase,
    private val playbackController: PlaybackController
): ViewModel() {
    private val _uiState = MutableStateFlow(ForYouUiState())
    val uiState: StateFlow<ForYouUiState> = _uiState

    init {
        loadForYouSong()
    }

    private fun loadForYouSong() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            val songs = getForYouSongsUseCase(AppUtil.DEFAULT_LIST_SIZE)
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