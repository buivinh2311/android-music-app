package com.example.core_playback

import android.content.Context
import android.media.AudioFocusRequest
import android.media.AudioManager
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
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
    @param:ApplicationContext private val context: Context
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
    override val player: Player
        get() = exoPlayer
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
                _playbackState.update {
                    it.copy(
                        currentSongId = mediaItem?.mediaId,
                        currentIndex = exoPlayer.currentMediaItemIndex,
                        currentPosition = 0L
                    )
                }
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

//    private var shouldResumeAfterFocusGain = false
//    private val audioManager = context.getSystemService(AudioManager::class.java)
//    private val audioFocusListener =
//        AudioManager.OnAudioFocusChangeListener { focusChange ->
//            when (focusChange) {
//                AudioManager.AUDIOFOCUS_LOSS -> pause()
//                AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
//                    if (exoPlayer.isPlaying) {
//                        shouldResumeAfterFocusGain = true
//                        pause()
//                    }
//                }
//                AudioManager.AUDIOFOCUS_GAIN -> {
//                    if (shouldResumeAfterFocusGain) {
//                        resume()
//                        shouldResumeAfterFocusGain = false
//                    }
//                }
//            }
//        }
//    private val audioFocusRequest = AudioFocusRequest.Builder(
//        AudioManager.AUDIOFOCUS_GAIN
//    ).setOnAudioFocusChangeListener(
//        audioFocusListener
//    ).build()

    override fun play(queueSource: String, queue: List<Song>, startSong: Song) {
//        val result = audioManager.requestAudioFocus(audioFocusRequest)
//        if (result != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
//            return
//        }

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
                queue = queue
            )
        }
    }

    override fun pause() {
        exoPlayer.pause()
    }

    override fun resume() {
        exoPlayer.play()
    }

    override fun seekTo(position: Long) {
        exoPlayer.seekTo(position)
    }

    override fun skipNext() {
        exoPlayer.seekToNextMediaItem()
    }

    override fun skipPrevious() {
        exoPlayer.seekToPreviousMediaItem()
    }

    override fun toggleShuffle() {
        exoPlayer.shuffleModeEnabled = !exoPlayer.shuffleModeEnabled
        _playbackState.update {
            it.copy(
                isShuffleEnabled = exoPlayer.shuffleModeEnabled
            )
        }
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
    }
}