package com.example.feature_player.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.Song
import com.example.core_playback.MediaPlaybackController
import com.example.core_ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QueueViewModel @Inject constructor(
    private val mediaPlaybackController: MediaPlaybackController
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Song>>>(
        UiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    val playbackState = mediaPlaybackController.playbackState

    init {
        loadQueue()
    }

    private fun loadQueue() {
        viewModelScope.launch {
            val queue = playbackState.first().playQueue
            _uiState.value = if(queue.isEmpty()) {
                UiState.Empty
            } else {
                UiState.Success(queue)
            }
        }
    }

    fun seekTo(mediaItemIndex: Int, position: Long) {
        mediaPlaybackController.seekTo(mediaItemIndex, position)
    }
}