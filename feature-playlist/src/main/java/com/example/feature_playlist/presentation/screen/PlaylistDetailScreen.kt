package com.example.feature_playlist.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_model.DisplaySong
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_ui.component.AppBottomBar
import com.example.core_ui.component.AppTopBar
import com.example.core_ui.component.SongItem
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_playlist.presentation.component.PlaylistInformation
import com.example.feature_playlist.presentation.viewmodel.PlaylistDetailViewModel
import com.example.shared_presentation.model.SongOptionItem
import com.example.shared_presentation.presentation.SongActionHost

@Composable
fun PlaylistDetailScreen(
    playlistId: Int,
    onSongClick: (String) -> Unit,
    onBackCLick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit,
    onSongNavigationAction: (SongOptionItem) -> Unit
) {
    var selectedSong: DisplaySong? by remember {
        mutableStateOf(null)
    }
    val playlistDetailViewModel: PlaylistDetailViewModel = hiltViewModel()
    val uiState by playlistDetailViewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(playlistId) {
        playlistDetailViewModel.loadPlaylist(playlistId)
    }
    val playlist = uiState.playlist
    val songs = uiState.songs
    val playlists by playlistDetailViewModel.playlists
        .collectAsStateWithLifecycle(emptyList())

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AppBottomBar(onBottomActionClick = onBottomActionClick)
        },
        topBar = {
            AppTopBar(
                title = playlist?.name ?: "",
                onBackClick = onBackCLick
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        if(uiState.isLoading) {

        } else {
            playlist?.let {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .background(MaterialTheme.colorScheme.background),
                    contentPadding = PaddingValues(
                        vertical = AppDimens.Space.Lg
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        PlaylistInformation(playlist)
                        Spacer(modifier = Modifier.height(AppDimens.Space.Sm))
                        Button(
                            onClick = {},
                            shape = RoundedCornerShape(48.dp),
                            modifier = Modifier
                                .width(200.dp)
                                .padding(horizontal = AppDimens.Space.Xl)
                        ) {
                            Text(
                                text = stringResource(R.string.action_play_music),
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.height(AppDimens.Space.Xl))
                    }

                    items(
                        count = songs.size,
                        key = { index -> songs[index].id }
                    ) { index ->
                        SongItem(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .fillMaxWidth(),
                            song = songs[index],
                            onSongClick = onSongClick,
                            onMoreClick = { song ->
                                selectedSong = song
                            }
                        )
                    }
                }
            }

            SongActionHost(
                selectedSong = selectedSong,
                playlists = playlists,
                observeFavoriteSong = { songId ->
                    playlistDetailViewModel.isFavoriteSong(songId)
                },
                onDismissSong = { selectedSong = null },
                onAddSongToFavorite = { songId ->
                    playlistDetailViewModel.addSongToFavorite(songId)
                },
                onRemoveSongFromFavorite = { songId ->
                    playlistDetailViewModel.removeSongToFavorite(songId)
                },
                onCreatePlaylist = {playlistName ->
                    playlistDetailViewModel.createPlaylist(playlistName)
                },
                onAddSongToPlaylist = {playlistId, songId ->
                    playlistDetailViewModel.addSongToPlaylist(playlistId, songId)
                },
                onSongNavigationAction = onSongNavigationAction
            )
        }
    }
}