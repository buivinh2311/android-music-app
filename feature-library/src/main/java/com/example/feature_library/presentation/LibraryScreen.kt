package com.example.feature_library.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_model.playback.QueueSource
import com.example.core_model.Song
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_resources.ui.icon.AppIcons
import com.example.core_ui.component.AppBottomBar
import com.example.core_ui.component.AppTopBar
import com.example.core_ui.component.EmptySection
import com.example.core_ui.component.LoadingScreen
import com.example.core_ui.component.ViewAllButton
import com.example.core_ui.component.showToast
import com.example.core_ui.menu.AppBottomBarAction
import com.example.core_ui.state.UiState
import com.example.feature_library.presentation.component.LibraryCategory
import com.example.feature_library.presentation.component.LibraryPlaylistHeader
import com.example.shared_presentation.menu.SongOptionItem
import com.example.shared_presentation.presentation.CreatePlaylistDialog
import com.example.shared_presentation.presentation.PlaylistItem
import com.example.shared_presentation.presentation.SongActionHost
import com.example.shared_presentation.presentation.SongLazyHorizontalGrid

@SuppressLint("LocalContextGetResourceValueCall")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    isConnect: Boolean,
    onSongOptionClick: (Song) -> Unit,
    onRecentClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onDownloadClick: () -> Unit,
    onFavoriteAlbumClick: () -> Unit,
    onFollowedArtistClick: () -> Unit,
    onMorePlaylistClick: () -> Unit,
    onPlaylistClick: (Int) -> Unit,
    onSearchClick: () -> Unit,
    onSongClick: (String) -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
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

    val uiState by libraryViewModel.uiState.collectAsStateWithLifecycle()

    val playlists by libraryViewModel.playlists
        .collectAsStateWithLifecycle(emptyList())

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
                    onDownloadClick = onDownloadClick,
                    onFavoriteAlbumClick = onFavoriteAlbumClick,
                    onFollowedArtistClick = onFollowedArtistClick
                )
                Spacer(modifier = Modifier.height(AppDimens.Space.Xl))
                ViewAllButton(
                    title = stringResource(R.string.title_library_heard_recently),
                    onMoreClick = onRecentClick
                )

                when(val state = uiState) {
                    UiState.Loading -> {
                        LoadingScreen(
                            modifier = Modifier.padding(innerPadding)
                        )
                    }

                    UiState.Empty -> {
                        EmptySection(
                            icon = AppIcons.Song,
                            title = stringResource(R.string.title_no_song_found)
                        )
                    }

                    is UiState.Success -> {
                        val recentSongs = state.data
                        SongLazyHorizontalGrid(
                            songs = recentSongs,
                            rowWidth = 300.dp,
                            onSongClick = { song ->
                                if(isConnect) {
                                    libraryViewModel.play(
                                        queueSource = QueueSource.RECENT,
                                        queue = recentSongs,
                                        startSong = song
                                    )
                                    onSongClick(song.id)
                                } else {
                                    showToast(
                                        context,
                                        message = context.getString(
                                            R.string.no_internet_message
                                        )
                                    )
                                }
                            },
                            onSongOptionClick = onSongOptionClick
                        )
                    }
                }
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

//        SongActionHost(
//            selectedSong = selectedSong,
//            playlists = playlists,
//            observeFavoriteSong = { songId ->
//                libraryViewModel.isFavoriteSong(songId)
//            },
//            onDismissSong = { selectedSong = null },
//            onAddSongToFavorite = { songId ->
//                libraryViewModel.addSongToFavorite(songId)
//            },
//            onRemoveSongFromFavorite = { songId ->
//                libraryViewModel.removeSongFromFavorite(songId)
//            },
//            onCreatePlaylist = {playlistName ->
//                libraryViewModel.createPlaylist(playlistName)
//            },
//            onAddSongToPlaylist = {playlistId, songId ->
//                libraryViewModel.addSongToPlaylist(playlistId, songId)
//            },
//            onSongNavigationAction = onSongNavigationAction
//        )
    }
}