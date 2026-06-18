package com.example.feature_search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_domain.usecase.FavoriteSongUseCases
import com.example.core_domain.usecase.PlaylistUseCases
import com.example.core_model.Song
import com.example.core_playback.PlaybackController
import com.example.core_playback.QueueSource
import com.example.feature_search.domain.AddSongToDatabaseUseCase
import com.example.feature_search.domain.AddSongToSearchSongUseCase
import com.example.feature_search.domain.ClearAllSearchSongsUseCase
import com.example.feature_search.domain.FindSongBySongNameOrArtistNameUseCase
import com.example.feature_search.domain.GetSearchSongsUseCase
import com.example.feature_search.presentation.SearchUiState
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
class SearchViewModel @Inject constructor(
    private val favoriteSongUseCases: FavoriteSongUseCases,
    private val playlistUseCases: PlaylistUseCases,
    getSearchSongsUseCase: GetSearchSongsUseCase,
    private val findSongBySongNameOrArtistNameUseCase: FindSongBySongNameOrArtistNameUseCase,
    private val addSongToSearchSongUseCase: AddSongToSearchSongUseCase,
    private val clearAllSearchSongsUseCase: ClearAllSearchSongsUseCase,
    private val addSongToDatabaseUseCase: AddSongToDatabaseUseCase,
    private val playbackController: PlaybackController
): ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState
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

    val searchSong = getSearchSongsUseCase()

    val playlists = playlistUseCases.getAllPlaylist()

    fun isFavoriteSong(songId: String) = favoriteSongUseCases.observerFavoriteSong(songId)

    fun findSongBySongNameOrArtistName(query: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            val songs = findSongBySongNameOrArtistNameUseCase(query)
            _uiState.update {
                it.copy(
                    songs = songs,
                    isLoading = false
                )
            }
        }
    }

    fun addSongToDataBase(song: Song) {
        viewModelScope.launch {
            addSongToDatabaseUseCase(song)
        }
    }

    fun addSongToSearchSong(songId: String) {
        viewModelScope.launch {
            addSongToSearchSongUseCase(songId)
        }
    }

    fun clearAllSearchSong() {
        viewModelScope.launch {
            clearAllSearchSongsUseCase()
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