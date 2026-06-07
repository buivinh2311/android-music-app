package com.example.core_playback

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.core_model.Song
import com.example.core_playback.state.PlaybackState
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
                    it.copy( currentPosition = player.currentPosition,
                        duration = player.duration
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

    override fun play(song: Song) {
        if(song.sourceUrl == null) return
        val mediaItem = MediaItem.Builder()
            .setUri(song.sourceUrl)
            .setMediaId(song.id)
            .build()
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
        _playbackState.update {
            it.copy(
                currentSongId = song.id,
                sourceUrl = song.sourceUrl
            )
        }
    }

    override fun pause() {
        player.pause()
    }

    override fun resume() {
        player.play()
    }
}