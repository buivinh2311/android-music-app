package com.example.feature_search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.Song
import com.example.core_model.playback.QueueSource
import com.example.core_playback.MediaPlaybackController
import com.example.core_ui.state.UiState
import com.example.feature_search.usecase.AddSongToDatabaseUseCase
import com.example.feature_search.usecase.AddSongToSearchSongUseCase
import com.example.feature_search.usecase.ClearAllSearchSongsUseCase
import com.example.feature_search.usecase.FindSongBySongNameOrArtistNameUseCase
import com.example.feature_search.usecase.GetSearchSongsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    getSearchSongsUseCase: GetSearchSongsUseCase,
    private val findSongBySongNameOrArtistNameUseCase: FindSongBySongNameOrArtistNameUseCase,
    private val addSongToSearchSongUseCase: AddSongToSearchSongUseCase,
    private val clearAllSearchSongsUseCase: ClearAllSearchSongsUseCase,
    private val addSongToDatabaseUseCase: AddSongToDatabaseUseCase,
    private val mediaPlaybackController: MediaPlaybackController
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Song>>>(
        UiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    val searchSong = getSearchSongsUseCase()

    fun findSongBySongNameOrArtistName(query: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val songs = findSongBySongNameOrArtistNameUseCase(query)
            _uiState.value = if(songs.isEmpty()) {
                UiState.Empty
            } else {
                UiState.Success(songs)
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

    fun play(queueSource: QueueSource, queue: List<Song>, startSong: Song) {
        mediaPlaybackController.play(queueSource, queue, startSong)
    }
}