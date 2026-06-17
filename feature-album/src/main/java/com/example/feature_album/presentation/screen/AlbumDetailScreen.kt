package com.example.feature_album.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_model.Album
import com.example.core_model.Song
import com.example.core_playback.QueueSource
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_ui.component.AppBottomBar
import com.example.core_ui.component.AppTopBar
import com.example.core_ui.component.SongItem
import com.example.core_ui.component.showToast
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_album.presentation.component.AlbumAction
import com.example.feature_album.presentation.component.AlbumInformation
import com.example.feature_album.presentation.viewmodel.AlbumDetailViewModel
import com.example.shared_presentation.model.SongOptionItem
import com.example.shared_presentation.presentation.MiniPlayer
import com.example.shared_presentation.presentation.SongActionHost

@SuppressLint("LocalContextGetResourceValueCall")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumDetailScreen(
    albumName: String,
    onSongClick: (String) -> Unit,
    onBackCLick: () -> Unit,
    onMiniPlayerClick: (String) -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit,
    onSongNavigationAction: (SongOptionItem) -> Unit

) {
    var selectedSong: Song? by remember {
        mutableStateOf(null)
    }
    val albumDetailViewModel: AlbumDetailViewModel = hiltViewModel()
    val uiState by albumDetailViewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(albumName) {
        albumDetailViewModel.loadAlbumDetail(albumName)
    }
    val songs = uiState.songs
    val album = uiState.album ?: Album(0, albumName, "", songs.size)
    val playlists by albumDetailViewModel.playlists
        .collectAsStateWithLifecycle(emptyList())

    val isFavoriteAlbum by albumDetailViewModel.isFavoriteAlbum(albumName)
        .collectAsStateWithLifecycle(false)

    val playbackState by albumDetailViewModel.playbackState
        .collectAsStateWithLifecycle()

    val isCurrentFavoriteSong by albumDetailViewModel.currentFavoriteSong
        .collectAsStateWithLifecycle()

    val currentSong = playbackState.queue.getOrNull(playbackState.currentIndex)
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AppBottomBar(onBottomActionClick = onBottomActionClick)
        },
        topBar = {
            AppTopBar(
                title = album.name,
                onBackClick = onBackCLick
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        if (uiState.isLoading) {

        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background),
                contentPadding = PaddingValues(
                    top = AppDimens.Space.Lg,
                    bottom = AppDimens.Space.bottomSpace
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    AlbumInformation(album)
                    Spacer(modifier = Modifier.height(AppDimens.Space.Md))
                    AlbumAction(
                        album = album,
                        isFavoriteAlbum = isFavoriteAlbum,
                        onFavoriteClick = { albumName ->
                            if(isFavoriteAlbum) {
                                albumDetailViewModel.removeAlbumFromFavorite(albumName)
                                showToast(
                                    context,
                                    message = context.getString(
                                        R.string.remove_album_from_favorite_success,
                                        albumName
                                    )
                                )
                            } else {
                                albumDetailViewModel.addAlbumToFavorite(albumName)
                                showToast(
                                    context,
                                    message = context.getString(
                                        R.string.add_album_from_favorite_success,
                                        albumName
                                    )
                                )
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(AppDimens.Space.Xl))
                }

                items(
                    count = songs.size,
                    key = { index -> songs[index].id }
                ) { index ->
                    SongItem(
                        modifier = Modifier
                            .padding(horizontal = AppDimens.Space.Xs)
                            .fillMaxWidth(),
                        song = songs[index],
                        onSongClick = { song ->
                            albumDetailViewModel.play(
                                queueSource = QueueSource.ALBUM,
                                queue = songs,
                                startSong = song
                            )
                            onSongClick(song.id)
                        },
                        onMoreClick = { song ->
                            selectedSong = song
                        }
                    )
                }
            }

            currentSong?.let {
                Box(
                    Modifier.fillMaxSize()
                ) {
                    MiniPlayer(
                        modifier = Modifier
                            .padding(innerPadding)
                            .align(Alignment.BottomCenter),
                        song = currentSong,
                        isFavoriteSong = isCurrentFavoriteSong,
                        isPlaying = playbackState.isPlaying,
                        onMiniPlayerClick = {
                            onMiniPlayerClick(currentSong.id)
                        },
                        onFavoriteClick = {
                            if(isCurrentFavoriteSong) {
                                albumDetailViewModel.removeSongFromFavorite(currentSong.id)
                                showToast(
                                    context,
                                    message = context.getString(
                                        R.string.remove_song_from_favorite_success,
                                        currentSong.title
                                    )
                                )
                            } else {
                                albumDetailViewModel.addSongToFavorite(currentSong.id)
                                showToast(
                                    context,
                                    message = context.getString(
                                        R.string.add_song_to_favorite_success,
                                        currentSong.title
                                    )
                                )
                            }
                        },
                        onTogglePlayClick = {
                            if(playbackState.isPlaying) {
                                albumDetailViewModel.pause()
                            } else {
                                albumDetailViewModel.resume()
                            }
                        },
                        onNextClick = {
                            albumDetailViewModel.skipNext()
                        }
                    )
                }
            }

            SongActionHost(
                selectedSong = selectedSong,
                playlists = playlists,
                observeFavoriteSong = { songId ->
                    albumDetailViewModel.isFavoriteSong(songId)
                },
                onDismissSong = { selectedSong = null },
                onAddSongToFavorite = { songId ->
                    albumDetailViewModel.addSongToFavorite(songId)
                },
                onRemoveSongFromFavorite = { songId ->
                    albumDetailViewModel.removeSongFromFavorite(songId)
                },
                onCreatePlaylist = { playlistName ->
                    albumDetailViewModel.createPlaylist(playlistName)
                },
                onAddSongToPlaylist = { playlistId, songId ->
                    albumDetailViewModel.addSongToPlaylist(playlistId, songId)
                },
                onSongNavigationAction = onSongNavigationAction
            )
        }
    }
}