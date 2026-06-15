package com.example.core_playback

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.core_model.Song
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
    @ApplicationContext context: Context,
): PlaybackController {
    private val player = ExoPlayer.Builder(context).build()
    private val _playbackState = MutableStateFlow(PlaybackState())
    override val playbackState: StateFlow<PlaybackState> = _playbackState

    private val scope = CoroutineScope(
        SupervisorJob() + Dispatchers.Main.immediate
    )
    init {
        scope.launch {
            while (true) {
                _playbackState.update {
                    it.copy(
                        currentPosition = player.currentPosition,
                    )
                }
                delay(500)
            }
        }

    }

    init {
        player.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _playbackState.update {
                    it.copy(isPlaying = isPlaying)
                }
            }

            override fun onMediaItemTransition(
                mediaItem: MediaItem?,
                reason: Int
            ) {
                _playbackState.update {
                    it.copy(
                        currentSongId = mediaItem?.mediaId,
                        currentIndex = player.currentMediaItemIndex,
                        currentPosition = 0L
                    )
                }
            }

            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_READY) {
                    _playbackState.update {
                        it.copy(
                            duration = player.duration
                        )
                    }
                }
            }

            override fun onPlayerError(error: PlaybackException) {
                // log + expose error state nếu cần
            }
        })
    }

    override fun play(queueSource: String, queue: List<Song>, startSong: Song) {
        val startIndex = queue.indexOfFirst { it.id == startSong.id }
        val mediaItems = queue.map { song ->
            MediaItem.Builder()
                .setMediaId(song.id)
                .setUri(song.sourceUrl)
                .build()
        }

        player.setMediaItems(
            mediaItems,
            startIndex,
            0L
        )

        player.prepare()
        player.play()

        _playbackState.update {
            it.copy(
                queueSource = queueSource,
                queue = queue
            )
        }
    }

    override fun pause() {
        player.pause()
    }

    override fun resume() {
        player.play()
    }

    override fun seekTo(position: Long) {
        player.seekTo(position)
    }

    override fun skipNext() {
        player.seekToNextMediaItem()
    }

    override fun skipPrevious() {
        player.seekToPreviousMediaItem()
    }

    override fun toggleShuffle() {
        player.shuffleModeEnabled = !player.shuffleModeEnabled
        _playbackState.update {
            it.copy(
                isShuffleEnabled = player.shuffleModeEnabled
            )
        }
    }

    override fun changeRepeatMode() {
        val nextMode = when(player.repeatMode) {
            Player.REPEAT_MODE_OFF -> Player.REPEAT_MODE_ALL
            Player.REPEAT_MODE_ALL -> Player.REPEAT_MODE_ONE
            else -> Player.REPEAT_MODE_OFF
        }
        player.repeatMode = nextMode
        _playbackState.update {
            it.copy(
                repeatMode = when(nextMode) {
                    Player.REPEAT_MODE_OFF -> RepeatMode.OFF
                    Player.REPEAT_MODE_ALL -> RepeatMode.ALL
                    else -> RepeatMode.ONE
                }
            )
        }
    }
}