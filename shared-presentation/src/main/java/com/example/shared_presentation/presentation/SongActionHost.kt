package com.example.shared_presentation.presentation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_model.Song
import com.example.core_model.Playlist
import com.example.core_resources.R
import com.example.core_ui.component.showToast
import com.example.shared_presentation.model.SongOptionAction
import com.example.shared_presentation.model.SongOptionItem
import kotlinx.coroutines.flow.Flow

@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun SongActionHost(
    selectedSong: Song?,
    playlists: List<Playlist>,
    observeFavoriteSong: (String) -> Flow<Boolean>,
    onDismissSong: () -> Unit,
    onAddSongToFavorite: (String) -> Unit,
    onRemoveSongFromFavorite: (String) -> Unit,
    onCreatePlaylist: (String) -> Unit,
    onAddSongToPlaylist: (Int, String) -> Unit,
    onSongNavigationAction: (SongOptionItem) -> Unit
) {
    var songForPlaylistPicker: Song? by remember {
        mutableStateOf(null)
    }

    var showCreatePlaylistDialog by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    selectedSong?.let { song ->
        val isFavorite by observeFavoriteSong(song.id)
            .collectAsStateWithLifecycle(false)
        SongOptionBottomSheet(
            song = song,
            isFavorite = isFavorite,
            onDismiss = { onDismissSong() },
            onShareClick = {
                showToast(
                    context,
                    message = context.getString(
                        R.string.currently_under_development,
                    )
                )
            },
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
                                    R.string.remove_song_from_favorite_success,
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

                    else -> {
                        showToast(
                            context,
                            message = context.getString(
                                R.string.currently_under_development,
                            )
                        )
                    }
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