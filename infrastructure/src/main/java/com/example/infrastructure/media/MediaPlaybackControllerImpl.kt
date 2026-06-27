package com.example.infrastructure.media

import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import com.example.core_model.Song
import com.example.core_playback.MediaControllerProvider
import com.example.core_playback.MediaPlaybackController
import com.example.core_model.playback.PlaybackState
import com.example.core_playback.PlaybackStateDataSource
import com.example.core_model.playback.QueueSource
import com.example.core_model.playback.RepeatMode
import com.example.core_playback.usecase.AddRecentSongUseCase
import com.example.core_playback.usecase.GetSongByIdUseCase
import com.example.core_playback.usecase.IncreasePlayCountUseCase
import com.example.core_playback.usecase.RestorePlaybackQueueUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.core.net.toUri

class MediaPlaybackControllerImpl @Inject constructor(
    private val playbackStateDataSource: PlaybackStateDataSource,
    private val restorePlaybackQueueUseCase: RestorePlaybackQueueUseCase,
    private val addRecentSongUseCase: AddRecentSongUseCase,
    private val increasePlayCountUseCase: IncreasePlayCountUseCase,
    private val getSongByIdUseCase: GetSongByIdUseCase,
    private val mediaControllerProvider: MediaControllerProvider
) : MediaPlaybackController {
    private val _playbackState = MutableStateFlow(PlaybackState())
    override val playbackState: StateFlow<PlaybackState> = _playbackState

    private val scope = CoroutineScope(
        SupervisorJob() + Dispatchers.Main.immediate
    )

    init {
        scope.launch {
            val mediaController = mediaControllerProvider.await()
            observePlayer(mediaController)
            restorePlayback(mediaController)
            observePosition(mediaController)
        }

    }

    private fun observePlayer(
        mediaController: MediaController
    ) {
        mediaController.addListener(object : Player.Listener {
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
                        currentIndex = mediaController.currentMediaItemIndex,
                        currentPosition = 0L
                    )
                }
                saveState()
            }

            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_READY) {
                    _playbackState.update {
                        it.copy(
                            duration = mediaController.duration
                        )
                    }
                }
            }

            override fun onPlayerError(error: PlaybackException) {
                // log + expose error state nếu cần
            }
        })
    }

    private suspend fun observePosition(
        controller: MediaController
    ) {
        while (true) {
            _playbackState.update {
                it.copy(
                    currentPosition =
                        controller.currentPosition
                )
            }
            delay(500)
        }
    }

    override fun play(
        queueSource: QueueSource,
        queue: List<Song>,
        startSong: Song,
        playlistId: Int?,
        sourceName: String?,
    ) {
        scope.launch {
            val mediaController = mediaControllerProvider.await()
            val startIndex = queue.indexOfFirst { it.id == startSong.id }
            val mediaItems = queue.map { song ->
                MediaItem.Builder()
                    .setMediaId(song.id)
                    .setUri(song.sourceUrl)
                    .setMediaMetadata(
                        MediaMetadata.Builder()
                            .setTitle(song.title)
                            .setArtist(song.artist)
                            .setAlbumTitle("")
                            .setArtworkUri(
                                song.artworkUrl.toUri()
                            )
                            .build()
                    )
                    .build()
            }

            mediaController.setMediaItems(
                mediaItems,
                startIndex,
                0L
            )

            mediaController.prepare()
            mediaController.play()

            _playbackState.update {
                it.copy(
                    queueSource = queueSource,
                    playlistId = playlistId ?: 0,
                    sourceName = sourceName,
                    queue = queue
                )
            }
        }
    }

    override fun pause() {
        scope.launch {
            mediaControllerProvider.await().pause()
            saveState()
        }
    }

    override fun resume() {
        scope.launch {
            mediaControllerProvider.await().play()
            saveState()
        }
    }

    override fun seekTo(position: Long) {
        scope.launch {
            mediaControllerProvider.await().seekTo(position)
            saveState()
        }
    }

    override fun skipNext() {
        scope.launch {
            mediaControllerProvider.await().seekToNextMediaItem()
            saveState()
        }
    }

    override fun skipPrevious() {
        scope.launch {
            mediaControllerProvider.await().seekToPreviousMediaItem()
            saveState()
        }
    }

    override fun toggleShuffle() {
        scope.launch {
            val mediaController = mediaControllerProvider.await()
            mediaController.shuffleModeEnabled = !mediaController.shuffleModeEnabled
            _playbackState.update {
                it.copy(
                    isShuffleEnabled = mediaController.shuffleModeEnabled
                )
            }
            saveState()
        }
    }

    override fun changeRepeatMode() {
        scope.launch {
            val mediaController = mediaControllerProvider.await()
            val nextMode = when (mediaController.repeatMode) {
                Player.REPEAT_MODE_OFF -> Player.REPEAT_MODE_ALL
                Player.REPEAT_MODE_ALL -> Player.REPEAT_MODE_ONE
                else -> Player.REPEAT_MODE_OFF
            }
            mediaController.repeatMode = nextMode
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
    }

    private fun saveState() {
        playbackStateDataSource.saveLastPlaybackState(playbackState.value)
    }

    private suspend fun restorePlayback(
        mediaController: MediaController
    ) {
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
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle(song.title)
                        .setArtist(song.artist)
                        .setAlbumTitle("")
                        .setArtworkUri(
                            song.artworkUrl.toUri()
                        )
                        .build()
                )
                .build()
        }

        mediaController.setMediaItems(
            mediaItems,
            currentIndex,
            state.currentPosition
        )
        mediaController.shuffleModeEnabled = state.isShuffleEnabled
        mediaController.repeatMode = when (state.repeatMode) {
            RepeatMode.ALL -> Player.REPEAT_MODE_ALL
            RepeatMode.ONE -> Player.REPEAT_MODE_ONE
            else -> Player.REPEAT_MODE_OFF
        }
        mediaController.prepare()
        if (state.isPlaying) {
            mediaController.play()
        }
    }
}