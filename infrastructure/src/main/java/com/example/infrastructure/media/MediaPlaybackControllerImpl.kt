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

    private fun toMediaItems(queue: List<Song>): List<MediaItem> {
        return queue.map { song ->
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
            val state = playbackState.value
            var playQueue = queue
            var startIndex = queue.indexOfFirst { it.id == startSong.id }
            if(state.isShuffleEnabled) {
                val shuffleQueue = state.originalQueue.filter {
                    it.id != startSong.id
                }.shuffled()
                playQueue = listOf(startSong) + shuffleQueue
                startIndex = 0
            }
            _playbackState.update {
                it.copy(
                    queueSource = queueSource,
                    playlistId = playlistId ?: 0,
                    currentIndex = 0,
                    sourceName = sourceName,
                    originalQueue = queue,
                    playQueue = playQueue
                )
            }

            val mediaItems = toMediaItems(playQueue)
            mediaController.setMediaItems(
                mediaItems,
                startIndex,
                0L
            )

            mediaController.prepare()
            mediaController.play()
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

    override fun seekTo(mediaItemIndex: Int, position: Long) {
        scope.launch {
            mediaControllerProvider.await().seekTo(mediaItemIndex, position)
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
            val state = playbackState.value
            val currentSong = state.playQueue[state.currentIndex]
//            val mediaController = mediaControllerProvider.await()
//            mediaController.shuffleModeEnabled = !mediaController.shuffleModeEnabled
            if(state.isShuffleEnabled) {
                val originalQueue = state.originalQueue
                val index = originalQueue.indexOfFirst {
                    it.id == currentSong.id
                }
                _playbackState.update {
                    it.copy(
//                    isShuffleEnabled = mediaController.shuffleModeEnabled
                        isShuffleEnabled = false,
                        currentIndex = index,
                        playQueue = originalQueue
                    )
                }
            } else {
                val shuffleQueue = state.originalQueue.filter {
                    it.id != currentSong.id
                }.shuffled()
                val playQueue = listOf(currentSong) + shuffleQueue
                _playbackState.update {
                    it.copy(
                        isShuffleEnabled = true,
                        currentIndex = 0,
                        playQueue = playQueue
                    )
                }
            }
            val newState = playbackState.value
            val currentPosition = newState.currentPosition
            val isPlaying = newState.isPlaying
            val mediaController = mediaControllerProvider.await()
            mediaController.setMediaItems(toMediaItems(newState.playQueue))
            mediaController.prepare()
            mediaController.seekTo(newState.currentIndex, currentPosition)
            if (isPlaying) {
                mediaController.play()
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

        var playQueue = queue
        if(state.isShuffleEnabled) {
            val shuffleQueue = queue.filter {
                it.id != currentSong.id
            }.shuffled()
            playQueue = listOf(currentSong) + shuffleQueue
            currentIndex = 0
        }

        _playbackState.value = state.copy(
            originalQueue = queue,
            playQueue = playQueue,
            currentIndex = currentIndex
        )

        val mediaItems = toMediaItems(playQueue)

        mediaController.setMediaItems(
            mediaItems,
            currentIndex,
            state.currentPosition
        )
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