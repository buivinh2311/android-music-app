package com.example.feature_discovery.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_domain.usecase.FavoriteSongUseCases
import com.example.core_domain.usecase.PlaylistUseCases
import com.example.core_model.Song
import com.example.core_playback.PlaybackController
import com.example.core_playback.QueueSource
import com.example.core_utils.util.AppUtil
import com.example.feature_discovery.domain.usecase.GetForYouSongsUseCase
import com.example.feature_discovery.domain.usecase.GetMostHeardSongsUseCase
import com.example.feature_discovery.domain.usecase.GetTopArtistUseCase
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
class DiscoveryViewModel @Inject constructor(
    private val favoriteSongUseCases: FavoriteSongUseCases,
    private val playlistUseCases: PlaylistUseCases,
    private val getTopArtistUseCase: GetTopArtistUseCase,
    private val getForYouSongsUseCase: GetForYouSongsUseCase,
    private val getMostHeardSongUseCase: GetMostHeardSongsUseCase,
    private val playbackController: PlaybackController
): ViewModel() {
    private val _uiState = MutableStateFlow(DiscoveryUiState())
    val uiState: StateFlow<DiscoveryUiState> = _uiState

    init {
        loadHotArtists()
        loadMostHeardSongs()
        loadForYouSongs()
    }

    private fun loadHotArtists() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            val artists = getTopArtistUseCase(AppUtil.SECTION_PAGE_SIZE)
            _uiState.update {
                it.copy(
                    hotArtists = artists,
                    isLoading = false
                )
            }
        }
    }

    private fun loadMostHeardSongs() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            val songs = getMostHeardSongUseCase(AppUtil.SECTION_PAGE_SIZE)
            _uiState.update {
                it.copy(
                    mostHeardSongs = songs,
                    isLoading = false
                )
            }
        }
    }

    private fun loadForYouSongs() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            val songs = getForYouSongsUseCase(AppUtil.SECTION_PAGE_SIZE)
            _uiState.update {
                it.copy(
                    forYouSongs = songs,
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