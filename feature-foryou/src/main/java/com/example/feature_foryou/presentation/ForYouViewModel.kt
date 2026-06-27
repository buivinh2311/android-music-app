package com.example.feature_foryou.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.Song
import com.example.core_model.playback.QueueSource
import com.example.core_playback.MediaPlaybackController
import com.example.core_ui.state.UiState
import com.example.core_utils.util.AppUtil
import com.example.feature_foryou.usecase.GetForYouSongsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForYouViewModel @Inject constructor(
    private val getForYouSongsUseCase: GetForYouSongsUseCase,
    private val mediaPlaybackController: MediaPlaybackController
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Song>>>(
        UiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    init {
        loadForYouSong()
    }

    private fun loadForYouSong() {
        viewModelScope.launch {
            val songs = getForYouSongsUseCase(AppUtil.DEFAULT_LIST_SIZE)
            _uiState.value = if (songs.isEmpty()) {
                UiState.Empty
            } else {
                UiState.Success(songs)
            }
        }
    }

    fun play(queueSource: QueueSource, queue: List<Song>, startSong: Song) {
        mediaPlaybackController.play(queueSource, queue, startSong)
    }

}