package com.example.feature_home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.Song
import com.example.core_model.playback.QueueSource
import com.example.core_playback.MediaPlaybackController
import com.example.core_ui.state.UiState
import com.example.core_utils.util.AppUtil
import com.example.feature_home.usecase.GetRecommendedSongsUseCase
import com.example.feature_home.usecase.GetTopAlbumUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTopAlbumUseCase: GetTopAlbumUseCase,
    private val getRecommendedSongsUseCase: GetRecommendedSongsUseCase,
    private val mediaPlaybackController: MediaPlaybackController
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState

    init {
        loadHotAlbums()
        loadRecommendedSongs()
    }

    fun loadHotAlbums() {
        viewModelScope.launch {
            val albums = getTopAlbumUseCase(AppUtil.SECTION_PAGE_SIZE)
            _uiState.update {
                it.copy(
                    hotAlbums = if(albums.isEmpty()) {
                        UiState.Empty
                    } else {
                        UiState.Success(albums)
                    }
                )
            }
        }
    }

    fun loadRecommendedSongs() {
        viewModelScope.launch {
            val songs = getRecommendedSongsUseCase(AppUtil.SECTION_PAGE_SIZE)
            _uiState.update {
                it.copy(
                    recommendedSongs = if(songs.isEmpty()) {
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