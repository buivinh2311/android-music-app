package com.example.feature_discovery.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.Song
import com.example.core_model.playback.QueueSource
import com.example.core_playback.MediaPlaybackController
import com.example.core_ui.state.UiState
import com.example.core_utils.util.AppUtil
import com.example.feature_discovery.usecase.GetForYouSongsUseCase
import com.example.feature_discovery.usecase.GetMostHeardSongsUseCase
import com.example.feature_discovery.usecase.GetTopArtistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoveryViewModel @Inject constructor(
    private val getTopArtistUseCase: GetTopArtistUseCase,
    private val getForYouSongsUseCase: GetForYouSongsUseCase,
    private val getMostHeardSongUseCase: GetMostHeardSongsUseCase,
    private val mediaPlaybackController: MediaPlaybackController
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
            val artists = getTopArtistUseCase(AppUtil.SECTION_PAGE_SIZE)
            _uiState.update {
                it.copy(
                    hotArtists = if(artists.isEmpty()) {
                        UiState.Empty
                    } else {
                        UiState.Success(artists)
                    }
                )
            }
        }
    }

    private fun loadMostHeardSongs() {
        viewModelScope.launch {
            val songs = getMostHeardSongUseCase(AppUtil.DEFAULT_LIST_SIZE)
            _uiState.update {
                it.copy(
                    mostHeardSongs = if(songs.isEmpty()) {
                        UiState.Empty
                    } else {
                        UiState.Success(songs)
                    }
                )
            }
        }
    }

    private fun loadForYouSongs() {
        viewModelScope.launch {
            val songs = getForYouSongsUseCase(AppUtil.SECTION_PAGE_SIZE)
            _uiState.update {
                it.copy(
                    forYouSongs = if(songs.isEmpty()) {
                        UiState.Empty
                    } else {
                        UiState.Success(songs)
                    }
                )
            }
        }
    }

    fun play(queueSource: QueueSource, queue: List<Song>, startSong: Song) {
        mediaPlaybackController.play(queueSource, queue, startSong)
    }
}