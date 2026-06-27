package com.example.feature_library.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_domain.usecase.PlaylistUseCases
import com.example.core_model.Song
import com.example.core_model.playback.QueueSource
import com.example.core_playback.MediaPlaybackController
import com.example.core_ui.state.UiState
import com.example.core_utils.util.AppUtil
import com.example.feature_library.usecase.GetDownloadSongCountUseCase
import com.example.feature_library.usecase.GetFavoriteAlbumCountUseCase
import com.example.feature_library.usecase.GetFavoriteSongCountUseCase
import com.example.feature_library.usecase.GetFollowedArtistCountUseCase
import com.example.feature_library.usecase.GetLimitPlaylistUseCase
import com.example.feature_library.usecase.GetLimitRecentSongsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    val downloadSongCount = getDownloadSongCountUseCase()
    val favoriteAlbumCount = getFavoriteAlbumCountUseCase()
    val favoriteSongCount = getFavoriteSongCountUseCase()
    val followedArtistCount = getFollowedArtistCountUseCase()
    val playlists = getLimitPlaylistUseCase(5)

    fun createPlaylist(playlistName: String) {
        viewModelScope.launch {
            playlistUseCases.createPlaylist(playlistName)
        }
    }

    fun play(queueSource: QueueSource, queue: List<Song>, startSong: Song) {
        mediaPlaybackController.play(queueSource, queue, startSong)
    }

}