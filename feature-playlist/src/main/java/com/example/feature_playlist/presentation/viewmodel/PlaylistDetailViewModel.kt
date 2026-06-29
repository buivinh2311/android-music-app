package com.example.feature_playlist.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.Playlist
import com.example.core_model.Song
import com.example.core_model.playback.QueueSource
import com.example.core_playback.MediaPlaybackController
import com.example.core_ui.state.UiState
import com.example.feature_playlist.usecase.DeletePlaylistUseCase
import com.example.feature_playlist.usecase.GetPlaylistByIdUseCase
import com.example.feature_playlist.usecase.GetSongInPlaylistUseCase
import com.example.feature_playlist.usecase.RemoveAllSongFromPlaylistUseCase
import com.example.feature_playlist.usecase.RenamePlaylistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistDetailViewModel @Inject constructor(
    private val getPlaylistByIdUseCase: GetPlaylistByIdUseCase,
    private val getSongInPlaylistUseCase: GetSongInPlaylistUseCase,
    private val deletePlaylistUseCase: DeletePlaylistUseCase,
    private val removeAllSongFromPlaylistUseCase: RemoveAllSongFromPlaylistUseCase,
    private val renamePlaylistUseCase: RenamePlaylistUseCase,
    private val mediaPlaybackController: MediaPlaybackController
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<Playlist>>(
        UiState.Loading
    )
    val uiState = _uiState.asStateFlow()
    fun songInPlaylist(playlistId: Int) = getSongInPlaylistUseCase(playlistId)

    fun observePlaylist(playlistId: Int) {
        viewModelScope.launch {
            getPlaylistByIdUseCase(playlistId).collect { playlist ->
                _uiState.value = if(playlist == null) {
                    UiState.Empty
                } else {
                    UiState.Success(playlist)
                }
            }
        }
    }

    fun play(
        queueSource: QueueSource,
        queue: List<Song>,
        startSong: Song,
        playlistId: Int,
        playlistName: String
    ) {
        mediaPlaybackController.play(queueSource, queue, startSong, playlistId, playlistName)
    }

    fun delete(playlistId: Int) {
        viewModelScope.launch {
            removeAllSongFromPlaylistUseCase(playlistId)
            deletePlaylistUseCase(playlistId)
        }
    }

    fun rename(playlistId: Int, newName: String) {
        viewModelScope.launch {
            renamePlaylistUseCase(playlistId, newName)
            val playlist = getPlaylistByIdUseCase(playlistId).first()
            Log.d("PLAYLIST_TEST", playlist.toString())
            _uiState.value = if(playlist != null) {
                UiState.Success(playlist)
            } else {
                UiState.Empty
            }
        }
    }
}