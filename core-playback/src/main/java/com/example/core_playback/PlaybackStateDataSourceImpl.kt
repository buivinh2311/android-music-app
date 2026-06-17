package com.example.core_playback

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.core_utils.util.AppUtil
import javax.inject.Inject

class PlaybackStateDataSourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
): PlaybackStateDataSource {
    override fun loadLastPlaybackState(): PlaybackState {
        val queueSource = when(sharedPreferences
            .getString(AppUtil.PREF_QUEUE_SOURCE, "_")) {
            "FAVORITE" -> QueueSource.FAVORITE
            "RECOMMENDED" -> QueueSource.RECOMMENDED
            "RECENT" -> QueueSource.RECENT
            "SEARCH" -> QueueSource.SEARCH
            "MOST_HEARD" -> QueueSource.MOST_HEARD
            "FOR_YOU" -> QueueSource.FOR_YOU
            "PLAYLIST" -> QueueSource.PLAYLIST
            "ARTIST" -> QueueSource.ARTIST
            "ALBUM" -> QueueSource.ALBUM
            "DOWNLOAD" -> QueueSource.DOWNLOAD
            else -> QueueSource.DEFAULT
        }
        val isPlaying = sharedPreferences
            .getBoolean(AppUtil.PREF_IS_PLAYING, false)
        val currentIndex = sharedPreferences
            .getInt(AppUtil.PREF_CURRENT_INDEX, 0)
        val currentSongId = sharedPreferences
            .getString(AppUtil.PREF_SONG_ID, null)
        val currentPosition = sharedPreferences
            .getLong(AppUtil.PREF_CURRENT_POSITION, 0)
        val artistName = sharedPreferences
            .getString(AppUtil.PREF_ARTIST_NAME, null)
        val albumName = sharedPreferences
            .getString(AppUtil.PREF_ALBUM_NAME, null)
        val playlistId = sharedPreferences
            .getInt(AppUtil.PREF_PLAYLIST_ID, 0)
        val sourceName = sharedPreferences
            .getString(AppUtil.PREF_SOURCE_NAME, null)
        val isShuffleEnabled = sharedPreferences
            .getBoolean(AppUtil.PREF_IS_SHUFFLE_ENABLED, false)
        val repeatMode: RepeatMode =
            when(sharedPreferences.getString(AppUtil.PREF_REPEAT_MODE, null)) {
                "ONE" -> RepeatMode.ONE
                "ALL" -> RepeatMode.ALL
                "OFF" -> RepeatMode.OFF
                else -> RepeatMode.OFF
            }
        return PlaybackState(
            queueSource = queueSource,
            isPlaying = isPlaying,
            currentIndex = currentIndex,
            currentSongId = currentSongId,
            currentPosition = currentPosition,
            artistName = artistName,
            albumName = albumName,
            playlistId = playlistId,
            sourceName = sourceName,
            isShuffleEnabled = isShuffleEnabled,
            repeatMode = repeatMode
        )
    }

    override fun saveLastPlaybackState(playbackState: PlaybackState) {
        val queueSource = playbackState.queueSource.name
        val repeatMode = when(playbackState.repeatMode) {
            RepeatMode.OFF -> "OFF"
            RepeatMode.ONE -> "ONE"
            RepeatMode.ALL -> "ALL"
        }
        sharedPreferences.edit {
            putString(AppUtil.PREF_QUEUE_SOURCE, queueSource)
                .putBoolean(AppUtil.PREF_IS_PLAYING, playbackState.isPlaying)
                .putInt(AppUtil.PREF_CURRENT_INDEX, playbackState.currentIndex)
                .putString(AppUtil.PREF_SONG_ID, playbackState.currentSongId)
                .putLong(AppUtil.PREF_CURRENT_POSITION, playbackState.currentPosition)
                .putString(AppUtil.PREF_ARTIST_NAME, playbackState.artistName)
                .putString(AppUtil.PREF_ALBUM_NAME, playbackState.albumName)
                .putInt(AppUtil.PREF_PLAYLIST_ID, playbackState.playlistId)
                .putString(AppUtil.PREF_SOURCE_NAME, playbackState.sourceName)
                .putBoolean(AppUtil.PREF_IS_SHUFFLE_ENABLED, playbackState.isShuffleEnabled)
                .putString(AppUtil.PREF_REPEAT_MODE, repeatMode)
        }
    }
}