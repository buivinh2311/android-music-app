package com.example.feature_library.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_domain.usecase.FavoriteSongUseCases
import com.example.core_domain.usecase.PlaylistUseCases
import com.example.core_model.Song
import com.example.core_playback.MediaPlaybackController
import com.example.core_model.QueueSource
import com.example.core_ui.state.UiState
import com.example.core_utils.util.AppUtil
import com.example.feature_library.domain.usecase.GetDownloadSongCountUseCase
import com.example.feature_library.domain.usecase.GetFavoriteAlbumCountUseCase
import com.example.feature_library.domain.usecase.GetFavoriteSongCountUseCase
import com.example.feature_library.domain.usecase.GetFollowedArtistCountUseCase
import com.example.feature_library.domain.usecase.GetLimitPlaylistUseCase
import com.example.feature_library.domain.usecase.GetLimitRecentSongsUseCase
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
class LibraryViewModel @Inject constructor(
    getLimitPlaylistUseCase: GetLimitPlaylistUseCase,
    private val getLimitRecentSongsUseCase: GetLimitRecentSongsUseCase,
    getDownloadSongCountUseCase: GetDownloadSongCountUseCase,
    getFavoriteAlbumCountUseCase: GetFavoriteAlbumCountUseCase,
    getFavoriteSongCountUseCase: GetFavoriteSongCountUseCase,
    getFollowedArtistCountUseCase: GetFollowedArtistCountUseCase,
    private val playlistUseCases: PlaylistUseCases,
    private val favoriteSongUseCases: FavoriteSongUseCases,
    private val mediaPlaybackController: MediaPlaybackController
): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Song>>>(
        UiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    init {
        observeRecentSongs()
    }

    private fun observeRecentSongs() {
        viewModelScope.launch {
            getLimitRecentSongsUseCase(AppUtil.SECTION_PAGE_SIZE).collect { songs ->
                _uiState.value = if (songs.isEmpty()) {
                    UiState.Empty
                } else {
                    UiState.Success(songs)
                }
            }
        }
    }

    val playbackState = mediaPlaybackController.playbackState
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

    val downloadSongCount = getDownloadSongCountUseCase()
    val favoriteAlbumCount = getFavoriteAlbumCountUseCase()
    val favoriteSongCount = getFavoriteSongCountUseCase()
    val followedArtistCount = getFollowedArtistCountUseCase()
    val playlists = getLimitPlaylistUseCase(5)

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
        mediaPlaybackController.play(queueSource, queue, startSong)
    }

    fun pause() {
        mediaPlaybackController.pause()
    }

    fun resume() {
        mediaPlaybackController.resume()
    }

    fun skipNext() {
        mediaPlaybackController.skipNext()
    }
}