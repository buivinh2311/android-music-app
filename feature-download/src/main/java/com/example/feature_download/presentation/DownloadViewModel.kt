package com.example.feature_download.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_download.usecase.GetDownloadSongInfoUseCase
import com.example.core_model.Song
import com.example.core_model.download.DownloadState
import com.example.core_model.playback.QueueSource
import com.example.core_playback.MediaPlaybackController
import com.example.core_ui.state.UiState
import com.example.feature_download.usecase.GetAllDownloadSongsUseCase
import com.example.feature_download.usecase.RemoveSongFromDownloadUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DownloadViewModel @Inject constructor(
    private val getAllDownloadSongsUseCase: GetAllDownloadSongsUseCase,
    private val getDownloadInfoUseCase: GetDownloadSongInfoUseCase,
    private val removerSongFromDownloadUseCase: RemoveSongFromDownloadUseCase,
    private val mediaPlaybackController: MediaPlaybackController
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Song>>>(
        UiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    init {
        observeDownloadSongs()
    }

    private fun observeDownloadSongs() {
        viewModelScope.launch {
            getAllDownloadSongsUseCase().collect { songs ->
                val downloadSongs = songs.mapNotNull { song ->
                    val info = getDownloadInfoUseCase(song.id)
                    if(info?.state == DownloadState.FAILED) {
                        removerSongFromDownloadUseCase(song.id)
                        null
                    } else if(info?.state == DownloadState.SUCCESSFUL) {
                        val localUri = info.localUri
                        if (localUri != null) {
                            song.copy(sourceUrl = localUri)
                        } else {
                            song
                        }
                    } else {
                        null
                    }
                }
                _uiState.value = if (downloadSongs.isEmpty()) {
                    UiState.Empty
                } else {
                    UiState.Success(downloadSongs)
                }
            }
        }
    }

    fun play(queueSource: QueueSource, queue: List<Song>, startSong: Song) {
        viewModelScope.launch {
            mediaPlaybackController.play(queueSource, queue, startSong)
        }
    }
}