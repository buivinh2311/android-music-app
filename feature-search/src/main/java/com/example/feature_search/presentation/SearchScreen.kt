package com.example.feature_search.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_model.Song
import com.example.core_playback.QueueSource
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_ui.component.AppBottomBar
import com.example.core_ui.component.SongItem
import com.example.core_ui.component.showToast
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_search.presentation.component.DeleteConfirmDialog
import com.example.feature_search.presentation.component.SearchTopBar
import com.example.shared_presentation.model.SongOptionItem
import com.example.shared_presentation.presentation.MiniPlayer
import com.example.shared_presentation.presentation.SongActionHost

@SuppressLint("LocalContextGetResourceValueCall")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen (
    onSongClick: (String) -> Unit,
    onMiniPlayerClick: (String) -> Unit,
    onBackClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit,
    onSongNavigationAction: (SongOptionItem) -> Unit
) {
    var selectedSong: Song? by remember {
        mutableStateOf(null)
    }

    var showConfirmDialog by remember {
        mutableStateOf(false)
    }

    val listState = rememberLazyListState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    LaunchedEffect(listState.isScrollInProgress) {
        if (listState.isScrollInProgress) {
            focusManager.clearFocus()
            keyboardController?.hide()
        }
    }
    val searchViewModel: SearchViewModel = hiltViewModel()
    val uiState by searchViewModel.uiState.collectAsStateWithLifecycle()

    val searchSong by searchViewModel.searchSong
        .collectAsStateWithLifecycle(emptyList())

    val playlists by searchViewModel.playlists
        .collectAsStateWithLifecycle(emptyList())

    val playbackState by searchViewModel.playbackState
        .collectAsStateWithLifecycle()

    val isCurrentFavoriteSong by searchViewModel.currentFavoriteSong
        .collectAsStateWithLifecycle()

    val currentSong = playbackState.queue.getOrNull(playbackState.currentIndex)
    val songs = uiState.songs
    val context = LocalContext.current
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AppBottomBar(onBottomActionClick = onBottomActionClick)
        },
        topBar = {
            SearchTopBar(
                onBackClick = onBackClick,
                onQueryChange = { query ->
                    searchViewModel.findSongBySongNameOrArtistName(query)
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        if(uiState.isLoading) {
            
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background),
                state = listState,
                contentPadding = PaddingValues(
                    bottom = AppDimens.Space.bottomSpace
                )
            ) {
                if(songs.isNotEmpty()) {
                    item {
                        Spacer(modifier = Modifier.height(AppDimens.Space.Lg))
                    }

                    items(
                        count = songs.size,
                        key = { index -> songs[index].id }
                    ) { index ->
                        SongItem(
                            modifier = Modifier.padding(horizontal = AppDimens.Space.Xs),
                            song = songs[index],
                            onSongClick = { song ->
                                keyboardController?.hide()
                                searchViewModel.addSongToDataBase(song)
                                searchViewModel.addSongToSearchSong(song.id)
                                searchViewModel.play(
                                    queueSource = QueueSource.SEARCH,
                                    queue = songs,
                                    startSong = song
                                )
                                onSongClick(song.id)
                            },
                            onMoreClick = { song ->
                                keyboardController?.hide()
                                selectedSong = song
                            }
                        )
                    }
                } else {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = AppDimens.Space.Lg),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.search_recently),
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.weight(1f)
                            )

                            TextButton(
                                onClick = { showConfirmDialog = true }
                            ) {
                                Text(
                                    text = stringResource(R.string.text_delete),
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }

                    items(
                        count = searchSong.size,
                        key = { index -> searchSong[index].id }
                    ) { index ->
                        SongItem(
                            modifier = Modifier.padding(horizontal = AppDimens.Space.Xs),
                            song = searchSong[index],
                            onSongClick = { song ->
                                keyboardController?.hide()
                                searchViewModel.play(
                                    queueSource = QueueSource.SEARCH,
                                    queue = searchSong,
                                    startSong = song
                                )
                                onSongClick(song.id)
                            },
                            onMoreClick = { song ->
                                keyboardController?.hide()
                                selectedSong = song
                            }
                        )
                    }
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
                                searchViewModel.removeSongFromFavorite(currentSong.id)
                                showToast(
                                    context,
                                    message = context.getString(
                                        R.string.remove_song_from_favorite_success,
                                        currentSong.title
                                    )
                                )
                            } else {
                                searchViewModel.addSongToFavorite(currentSong.id)
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
                                searchViewModel.pause()
                            } else {
                                searchViewModel.resume()
                            }
                        },
                        onNextClick = {
                            searchViewModel.skipNext()
                        }
                    )
                }
            }

            if(showConfirmDialog) {
                DeleteConfirmDialog(
                    onDismiss = { showConfirmDialog = false },
                    onConfirm = {
                        searchViewModel.clearAllSearchSong()
                        showToast(
                            context,
                            message = context.getString(
                                R.string.delete_search_song_success
                            )
                        )
                    }
                )
            }

            SongActionHost(
                selectedSong = selectedSong,
                playlists = playlists,
                observeFavoriteSong = { songId ->
                    searchViewModel.isFavoriteSong(songId)
                },
                onDismissSong = { selectedSong = null },
                onAddSongToFavorite = { songId ->
                    searchViewModel.addSongToFavorite(songId)
                },
                onRemoveSongFromFavorite = { songId ->
                    searchViewModel.removeSongFromFavorite(songId)
                },
                onCreatePlaylist = {playlistName ->
                    searchViewModel.createPlaylist(playlistName)
                },
                onAddSongToPlaylist = {playlistId, songId ->
                    searchViewModel.addSongToPlaylist(playlistId, songId)
                },
                onSongNavigationAction = onSongNavigationAction
            )
        }
    }
}