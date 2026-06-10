package com.example.shared_presentation.presentation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.core_model.DisplaySong
import com.example.core_model.Playlist
import com.example.core_resources.R
import com.example.core_ui.component.showToast
import com.example.shared_presentation.model.SongOptionAction
import com.example.shared_presentation.model.SongOptionItem
import kotlinx.coroutines.flow.Flow

@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun SongActionHost(
    selectedSong: DisplaySong?,
    playlists: List<Playlist>,
    observeFavoriteSong: (String) -> Flow<Boolean>,
    onDismissSong: () -> Unit,
    onAddSongToFavorite: (String) -> Unit,
    onRemoveSongFromFavorite: (String) -> Unit,
    onCreatePlaylist: (String) -> Unit,
    onAddSongToPlaylist: (Int, String) -> Unit,
    onSongNavigationAction: (SongOptionItem) -> Unit
) {
    var songForPlaylistPicker: DisplaySong? by remember {
        mutableStateOf(null)
    }

    var showCreatePlaylistDialog by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    selectedSong?.let { song ->
        val isFavorite by observeFavoriteSong(song.id).collectAsState(false)
        SongOptionBottomSheet(
            song = song,
            onDismiss = { onDismissSong() },
            onSongNavigationAction = {
                onSongNavigationAction(it)
            },
            onSongBusinessAction = { item ->
                when (item.action) {
                    SongOptionAction.DOWNLOAD -> {
                        showToast(
                            context,
                            message = context.getString(
                                R.string.currently_under_development,
                            )
                        )
                    }

                    SongOptionAction.ADD_TO_LIBRARY -> {
                        if(isFavorite) {
                            onRemoveSongFromFavorite(song.id)
                            showToast(
                                context,
                                message = context.getString(
                                    R.string.remove_song_to_favorite_success,
                                    song.title
                                )
                            )
                        } else {
                            onAddSongToFavorite(song.id)
                            showToast(
                                context,
                                message = context.getString(
                                    R.string.add_song_to_favorite_success,
                                    song.title
                                )
                            )
                        }
                    }

                    SongOptionAction.ADD_TO_PLAYLIST -> {
                        songForPlaylistPicker = song
                    }

                    SongOptionAction.COMMENT -> {
                        showToast(
                            context,
                            message = context.getString(
                                R.string.currently_under_development,
                            )
                        )
                    }

                    SongOptionAction.REPORT -> {
                        showToast(
                            context,
                            message = context.getString(
                                R.string.currently_under_development,
                            )
                        )
                    }

                    else -> {}
                }
            }
        )
    }

    songForPlaylistPicker?.let { song ->
        PlaylistPickerBottomSheet(
            playlists = playlists,
            onDismiss = { songForPlaylistPicker = null },
            onPlaylistClick = { playlistId ->
                onAddSongToPlaylist(playlistId, song.id)
                showToast(
                    context,
                    message = context.getString(
                        R.string.add_song_to_playlist_success,
                        song.title
                    )
                )
            },
            onCreateNewPlaylist = {
                showCreatePlaylistDialog = true
            }
        )
    }

    if (showCreatePlaylistDialog) {
        CreatePlaylistDialog(
            onDismiss = { showCreatePlaylistDialog = false },
            onCreate = { playlistName ->
                onCreatePlaylist(playlistName)
                showToast(
                    context,
                    message = context.getString(
                        R.string.create_new_playlist_success
                    )
                )
            }
        )
    }
}