package com.example.feature_discovery.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_domain.usecase.FavoriteSongUseCases
import com.example.core_domain.usecase.PlaylistUseCases
import com.example.core_model.Playlist
import com.example.core_utils.util.AppUtil
import com.example.feature_discovery.domain.usecase.GetForYouSongsUseCase
import com.example.feature_discovery.domain.usecase.GetTopArtistUseCase
import com.example.feature_discovery.domain.usecase.GetMostHeardSongsUseCase
import com.example.feature_discovery.presentation.state.DiscoveryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
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
    private val getMostHeardSongUseCase: GetMostHeardSongsUseCase
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

    val playlists: StateFlow<List<Playlist>> =
        playlistUseCases.getAllPlaylist()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    fun isFavoriteSong(songId: String): Flow<Boolean> {
        return favoriteSongUseCases.observerFavoriteSong(songId)
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