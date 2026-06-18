package com.example.feature_library.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_model.Song
import com.example.core_playback.QueueSource
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_ui.component.AppBottomBar
import com.example.core_ui.component.AppTopBar
import com.example.core_ui.component.PlaylistItem
import com.example.core_ui.component.SongLazyHorizontalGrid
import com.example.core_ui.component.ViewAllButton
import com.example.core_ui.component.showToast
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_library.presentation.component.LibraryCategory
import com.example.feature_library.presentation.component.LibraryPlaylistHeader
import com.example.shared_presentation.model.SongOptionItem
import com.example.shared_presentation.presentation.CreatePlaylistDialog
import com.example.shared_presentation.presentation.MiniPlayer
import com.example.shared_presentation.presentation.SongActionHost

@SuppressLint("LocalContextGetResourceValueCall")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    onRecentClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onFavoriteAlbumClick: () -> Unit,
    onFollowedArtistClick: () -> Unit,
    onMorePlaylistClick: () -> Unit,
    onPlaylistClick: (Int) -> Unit,
    onSearchClick: () -> Unit,
    onSongClick: (String) -> Unit,
    onMiniPlayerClick: (String) -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit,
    onSongNavigationAction: (SongOptionItem) -> Unit
) {
    var selectedSong: Song? by remember {
        mutableStateOf(null)
    }

    var showCreatePlaylistDialog by remember {
        mutableStateOf(false)
    }

    val libraryViewModel: LibraryViewModel = hiltViewModel()
    val favoriteSongCount by libraryViewModel.favoriteSongCount
        .collectAsStateWithLifecycle(0)

    val downloadSongCount by libraryViewModel.downloadSongCount
        .collectAsStateWithLifecycle(0)

    val favoriteAlbumCount by libraryViewModel.favoriteAlbumCount
        .collectAsStateWithLifecycle(0)

    val followedArtistCount by libraryViewModel.followedArtistCount
        .collectAsStateWithLifecycle(0)

    val recentSongs by libraryViewModel.recentSongs
        .collectAsStateWithLifecycle(emptyList())

    val playlists by libraryViewModel.playlists
        .collectAsStateWithLifecycle(emptyList())

    val playbackState by libraryViewModel.playbackState
        .collectAsStateWithLifecycle()

    val isCurrentFavoriteSong by libraryViewModel.currentFavoriteSong
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
                title = stringResource(R.string.title_library),
                onSearchClick = onSearchClick
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(
                top = AppDimens.Space.Lg,
                bottom = AppDimens.Space.bottomSpace
            )
        ) {
            item {
                LibraryCategory(
                    favoriteSongCount = favoriteSongCount,
                    downloadSongCount = downloadSongCount,
                    favoriteAlbumCount = favoriteAlbumCount,
                    followedArtistCount = followedArtistCount,
                    onFavoriteClick = onFavoriteClick,
                    onFavoriteAlbumClick = onFavoriteAlbumClick,
                    onFollowedArtistClick = onFollowedArtistClick
                )
                Spacer(modifier = Modifier.height(AppDimens.Space.Xl))
                ViewAllButton(
                    title = stringResource(R.string.title_library_heard_recently),
                    onMoreClick = onRecentClick
                )
                SongLazyHorizontalGrid(
                    songs = recentSongs,
                    rowWidth = 300.dp,
                    onSongClick = { song ->
                        libraryViewModel.play(
                            queueSource = QueueSource.RECENT,
                            queue = recentSongs,
                            startSong = song
                        )
                        onSongClick(song.id)
                    },
                    onMoreClick = { song ->
                        selectedSong = song
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(AppDimens.Space.Xl))
                LibraryPlaylistHeader(
                    modifier = Modifier.padding(horizontal = AppDimens.Space.Xs),
                    onCreatePlaylistClick = { showCreatePlaylistDialog = true },
                    onMorePlaylistClick = onMorePlaylistClick
                )
            }

            items(
                count = playlists.size,
                key = { index -> playlists[index].id }
            ) { index ->
                PlaylistItem(
                    modifier = Modifier.padding(horizontal = AppDimens.Space.Xs),
                    playlist = playlists[index],
                    onPlaylistClick = onPlaylistClick
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
                            libraryViewModel.removeSongFromFavorite(currentSong.id)
                            showToast(
                                context,
                                message = context.getString(
                                    R.string.remove_song_from_favorite_success,
                                    currentSong.title
                                )
                            )
                        } else {
                            libraryViewModel.addSongToFavorite(currentSong.id)
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
                            libraryViewModel.pause()
                        } else {
                            libraryViewModel.resume()
                        }
                    },
                    onNextClick = {
                        libraryViewModel.skipNext()
                    }
                )
            }
        }

        if(showCreatePlaylistDialog) {
            CreatePlaylistDialog(
                onDismiss = { showCreatePlaylistDialog = false },
                onCreate = { playlistName ->
                    libraryViewModel.createPlaylist(playlistName)
                    showToast(
                        context,
                        message = context.getString(
                            R.string.create_new_playlist_success
                        )
                    )
                }
            )
        }

        SongActionHost(
            selectedSong = selectedSong,
            playlists = playlists,
            observeFavoriteSong = { songId ->
                libraryViewModel.isFavoriteSong(songId)
            },
            onDismissSong = { selectedSong = null },
            onAddSongToFavorite = { songId ->
                libraryViewModel.addSongToFavorite(songId)
            },
            onRemoveSongFromFavorite = { songId ->
                libraryViewModel.removeSongFromFavorite(songId)
            },
            onCreatePlaylist = {playlistName ->
                libraryViewModel.createPlaylist(playlistName)
            },
            onAddSongToPlaylist = {playlistId, songId ->
                libraryViewModel.addSongToPlaylist(playlistId, songId)
            },
            onSongNavigationAction = onSongNavigationAction
        )
    }
}