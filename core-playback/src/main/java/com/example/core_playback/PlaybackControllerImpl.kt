package com.example.core_playback

import android.content.Context
import android.util.Log
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.core_model.Song
import com.example.core_playback.usecase.AddRecentSongUseCase
import com.example.core_playback.usecase.GetSongByIdUseCase
import com.example.core_playback.usecase.IncreasePlayCountUseCase
import com.example.core_playback.usecase.RestorePlaybackQueueUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlaybackControllerImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val playbackStateDataSource: PlaybackStateDataSource,
    private val restorePlaybackQueueUseCase: RestorePlaybackQueueUseCase,
    private val addRecentSongUseCase: AddRecentSongUseCase,
    private val increasePlayCountUseCase: IncreasePlayCountUseCase,
    private val getSongByIdUseCase: GetSongByIdUseCase
) : PlaybackController {
    private val exoPlayer = ExoPlayer.Builder(context).build().apply {
        setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(C.USAGE_MEDIA)
                .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
                .build(),
            true
        )
    }
    private val _playbackState = MutableStateFlow(PlaybackState())
    override val playbackState: StateFlow<PlaybackState> = _playbackState

    private val scope = CoroutineScope(
        SupervisorJob() + Dispatchers.Main.immediate
    )

    init {
        scope.launch {
            restorePlayback()
            while (true) {
                _playbackState.update {
                    it.copy(
                        currentPosition = exoPlayer.currentPosition,
                    )
                }
                delay(500)
            }
        }

    }

    init {
        exoPlayer.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _playbackState.update {
                    it.copy(isPlaying = isPlaying)
                }
            }

            override fun onMediaItemTransition(
                mediaItem: MediaItem?,
                reason: Int
            ) {
                mediaItem?.mediaId?.let { songId ->
                    scope.launch {
                        addRecentSongUseCase(songId)
                        increasePlayCountUseCase(songId)
                    }
                }
                _playbackState.update {
                    it.copy(
                        currentSongId = mediaItem?.mediaId,
                        currentIndex = exoPlayer.currentMediaItemIndex,
                        currentPosition = 0L
                    )
                }
                saveState()
            }

            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_READY) {
                    _playbackState.update {
                        it.copy(
                            duration = exoPlayer.duration
                        )
                    }
                }
            }

            override fun onPlayerError(error: PlaybackException) {
                // log + expose error state nếu cần
            }
        })
    }

    override fun play(
        queueSource: QueueSource,
        queue: List<Song>,
        startSong: Song,
        playlistId: Int?,
        sourceName: String?,
    ) {
        val startIndex = queue.indexOfFirst { it.id == startSong.id }
        val mediaItems = queue.map { song ->
            MediaItem.Builder()
                .setMediaId(song.id)
                .setUri(song.sourceUrl)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle(song.title)
                        .setArtist(song.artist)
                        .build()
                )
                .build()
        }

        exoPlayer.setMediaItems(
            mediaItems,
            startIndex,
            0L
        )

        exoPlayer.prepare()
        exoPlayer.play()

        _playbackState.update {
            it.copy(
                queueSource = queueSource,
                playlistId = playlistId ?: 0,
                sourceName = sourceName,
                queue = queue
            )
        }
    }

    override fun pause() {
        exoPlayer.pause()
        saveState()
    }

    override fun resume() {
        exoPlayer.play()
        saveState()
    }

    override fun seekTo(position: Long) {
        exoPlayer.seekTo(position)
        saveState()
    }

    override fun skipNext() {
        exoPlayer.seekToNextMediaItem()
        saveState()
    }

    override fun skipPrevious() {
        exoPlayer.seekToPreviousMediaItem()
        saveState()
    }

    override fun toggleShuffle() {
        exoPlayer.shuffleModeEnabled = !exoPlayer.shuffleModeEnabled
        _playbackState.update {
            it.copy(
                isShuffleEnabled = exoPlayer.shuffleModeEnabled
            )
        }
        saveState()
    }

    override fun changeRepeatMode() {
        val nextMode = when (exoPlayer.repeatMode) {
            Player.REPEAT_MODE_OFF -> Player.REPEAT_MODE_ALL
            Player.REPEAT_MODE_ALL -> Player.REPEAT_MODE_ONE
            else -> Player.REPEAT_MODE_OFF
        }
        exoPlayer.repeatMode = nextMode
        _playbackState.update {
            it.copy(
                repeatMode = when (nextMode) {
                    Player.REPEAT_MODE_OFF -> RepeatMode.OFF
                    Player.REPEAT_MODE_ALL -> RepeatMode.ALL
                    else -> RepeatMode.ONE
                }
            )
        }
        saveState()
    }

    private fun saveState() {
        val state = playbackState.value
        playbackStateDataSource.saveLastPlaybackState(playbackState.value)
    }

    private suspend fun restorePlayback() {
        val state = playbackStateDataSource.loadLastPlaybackState()
        var queue = restorePlaybackQueueUseCase(state)
        _playbackState.value = state.copy(
            queue = queue
        )
        val songId = state.currentSongId ?: return
        val currentSong = getSongByIdUseCase(songId) ?: return
        var currentIndex = queue.indexOfFirst { song ->
            song.id == currentSong.id
        }
        if (currentIndex == -1) {
            queue = buildList {
                add(currentSong)
                addAll(queue)
            }
            currentIndex = 0
        }

        _playbackState.value = state.copy(
            queue = queue,
            currentIndex = currentIndex
        )

        val mediaItems = queue.map { song ->
            MediaItem.Builder()
                .setMediaId(song.id)
                .setUri(song.sourceUrl)
                .build()
        }

        exoPlayer.setMediaItems(
            mediaItems,
            currentIndex,
            state.currentPosition
        )
        exoPlayer.shuffleModeEnabled = state.isShuffleEnabled
        exoPlayer.repeatMode = when (state.repeatMode) {
            RepeatMode.ALL -> Player.REPEAT_MODE_ALL
            RepeatMode.ONE -> Player.REPEAT_MODE_ONE
            else -> Player.REPEAT_MODE_OFF
        }
        exoPlayer.prepare()
        if (state.isPlaying) {
            exoPlayer.play()
        }
    }
}