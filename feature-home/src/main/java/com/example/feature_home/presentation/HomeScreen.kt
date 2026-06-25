package com.example.feature_home.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_model.QueueSource
import com.example.core_model.Song
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_resources.ui.icon.AppIcons
import com.example.core_ui.component.AppBottomBar
import com.example.core_ui.component.AppTopBar
import com.example.core_ui.component.EmptySection
import com.example.core_ui.component.LoadingScreen
import com.example.core_ui.component.ViewAllButton
import com.example.core_ui.menu.AppBottomBarAction
import com.example.core_ui.state.UiState
import com.example.shared_presentation.menu.SongOptionItem
import com.example.shared_presentation.presentation.AlbumItem
import com.example.shared_presentation.presentation.SongActionHost
import com.example.shared_presentation.presentation.SongItem

@SuppressLint("LocalContextGetResourceValueCall")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onMoreAlbumClick: () -> Unit,
    onAlbumClick: (String) -> Unit,
    onRecommendedClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSongClick: (String) -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit,
    onSongNavigationAction: (SongOptionItem) -> Unit
) {
    var selectedSong: Song? by remember {
        mutableStateOf(null)
    }
    val homeViewModel: HomeViewModel = hiltViewModel()
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        Log.d("HOME", "Composed")
    }
    val playlists by homeViewModel.playlists
        .collectAsStateWithLifecycle(emptyList())

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.title_home),
                onSearchClick = onSearchClick
            )
        },
        bottomBar = {
            AppBottomBar(onBottomActionClick = onBottomActionClick)
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        val isLoading = uiState.hotAlbums is UiState.Loading ||
                uiState.recommendedSongs is UiState.Loading
        if(isLoading) {
            LoadingScreen(modifier = Modifier.padding(innerPadding))
        } else {
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
                    ViewAllButton(
                        title = stringResource(R.string.title_home_popular_album),
                        onMoreClick = onMoreAlbumClick
                    )
                    when(val state = uiState.hotAlbums) {
                        UiState.Loading -> {

                        }

                        UiState.Empty -> {
                            EmptySection(
                                modifier = Modifier.padding(innerPadding),
                                icon = AppIcons.Album,
                                title = stringResource(R.string.title_no_album_found)
                            )
                        }

                        is UiState.Success -> {
                            val hotAlbums = state.data
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = AppDimens.Space.Lg),
                                horizontalArrangement = Arrangement.spacedBy(AppDimens.Space.Lg)
                            ) {
                                items(
                                    count = hotAlbums.size,
                                    key = { index -> hotAlbums[index].id }
                                ) { index ->
                                    AlbumItem(
                                        modifier = Modifier.width(150.dp),
                                        album = hotAlbums[index],
                                        titleMinLines = 2,
                                        onAlbumClick = onAlbumClick
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(AppDimens.Space.Lg))
                }

                item {
                    ViewAllButton(
                        title = stringResource(R.string.title_home_recommended_song),
                        onMoreClick = onRecommendedClick
                    )
                }

                when(val state = uiState.recommendedSongs) {
                    UiState.Loading -> {

                    }

                    UiState.Empty -> {
                        item {
                            EmptySection(
                                modifier = Modifier.padding(innerPadding),
                                icon = AppIcons.Song,
                                title = stringResource(R.string.title_no_song_found)
                            )
                        }
                    }

                    is UiState.Success -> {
                        val recommendedSongs = state.data
                        items(
                            count = recommendedSongs.size,
                            key = { index -> recommendedSongs[index].id }
                        ) { index ->
                            SongItem(
                                modifier = Modifier.padding(horizontal = AppDimens.Space.Xs),
                                song = recommendedSongs[index],
                                onSongClick = { song ->
                                    homeViewModel.play(
                                        queueSource = QueueSource.RECOMMENDED,
                                        queue = recommendedSongs,
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
                }
            }
        }

        SongActionHost(
            selectedSong = selectedSong,
            playlists = playlists,
            observeFavoriteSong = { songId ->
                homeViewModel.isFavoriteSong(songId)
            },
            onDismissSong = { selectedSong = null },
            onAddSongToFavorite = { songId ->
                homeViewModel.addSongToFavorite(songId)
            },
            onRemoveSongFromFavorite = { songId ->
                homeViewModel.removeSongFromFavorite(songId)
            },
            onCreatePlaylist = { playlistName ->
                homeViewModel.createPlaylist(playlistName)
            },
            onAddSongToPlaylist = {playlistId, songId ->
                homeViewModel.addSongToPlaylist(playlistId, songId)
            },
            onSongNavigationAction = onSongNavigationAction
        )
    }
}