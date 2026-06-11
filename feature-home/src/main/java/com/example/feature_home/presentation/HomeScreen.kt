package com.example.feature_home.presentation

import android.annotation.SuppressLint
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_model.DisplaySong
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_ui.component.AlbumItem
import com.example.core_ui.component.AppBottomBar
import com.example.core_ui.component.AppTopBar
import com.example.core_ui.component.SongItem
import com.example.core_ui.component.ViewAllButton
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_home.presentation.HomeViewModel
import com.example.shared_presentation.model.SongOptionItem
import com.example.shared_presentation.presentation.SongActionHost

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
    var selectedSong: DisplaySong? by remember {
        mutableStateOf(null)
    }
    val homeViewModel: HomeViewModel = hiltViewModel()
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val hotAlbums = uiState.hotAlbums
    val recommendedSongs = uiState.recommendedSongs
    val playlists by homeViewModel.playlists
        .collectAsStateWithLifecycle(emptyList())

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AppBottomBar(onBottomActionClick = onBottomActionClick)
        },
        topBar = {
            AppTopBar(title = stringResource(R.string.title_home))
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
                    vertical = AppDimens.Space.Lg
                )
            ) {
                item {
                    ViewAllButton(
                        title = stringResource(R.string.title_home_popular_album),
                        onMoreClick = onMoreAlbumClick
                    )
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
                    Spacer(modifier = Modifier.height(AppDimens.Space.Lg))
                    ViewAllButton(
                        title = stringResource(R.string.title_home_recommended_song),
                        onMoreClick = onRecommendedClick
                    )
                }

                items(
                    count = recommendedSongs.size,
                    key = { index -> recommendedSongs[index].id }
                ) { index ->
                    SongItem(
                        modifier = Modifier.padding(horizontal = AppDimens.Space.Xs),
                        song = recommendedSongs[index],
                        onSongClick = onSongClick,
                        onMoreClick = { song ->
                            selectedSong = song
                        }
                    )
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
                    homeViewModel.removeSongToFavorite(songId)
                },
                onCreatePlaylist = {playlistName ->
                    homeViewModel.createPlaylist(playlistName)
                },
                onAddSongToPlaylist = {playlistId, songId ->
                    homeViewModel.addSongToPlaylist(playlistId, songId)
                },
                onSongNavigationAction = onSongNavigationAction
            )
        }
    }
}