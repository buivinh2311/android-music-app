package com.example.feature_playlist.presentation.screen

import android.annotation.SuppressLint
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_model.Song
import com.example.core_model.playback.QueueSource
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_resources.ui.icon.AppIcons
import com.example.core_ui.component.AppBottomBar
import com.example.core_ui.component.AppTopBar
import com.example.core_ui.component.EmptySection
import com.example.shared_presentation.presentation.SongItem
import com.example.core_ui.component.showToast
import com.example.core_ui.menu.AppBottomBarAction
import com.example.core_ui.state.UiState
import com.example.feature_playlist.presentation.component.PlaylistInformation
import com.example.feature_playlist.presentation.viewmodel.PlaylistDetailViewModel
import com.example.shared_presentation.menu.SongOptionItem
import com.example.shared_presentation.presentation.SongActionHost

@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun PlaylistDetailScreen(
    playlistId: Int,
    isConnect: Boolean,
    onSongClick: (String) -> Unit,
    onSongOptionClick: (Song) -> Unit,
    onBackCLick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    var selectedSong: Song? by remember {
        mutableStateOf(null)
    }
    val playlistDetailViewModel: PlaylistDetailViewModel = hiltViewModel()
    val uiState by playlistDetailViewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(playlistId) {
        playlistDetailViewModel.loadPlaylist(playlistId)
    }

    val playlists by playlistDetailViewModel.playlists
        .collectAsStateWithLifecycle(emptyList())

    val songsInPlaylist by playlistDetailViewModel.songInPlaylist(playlistId)
        .collectAsStateWithLifecycle(emptyList())

    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AppBottomBar(onBottomActionClick = onBottomActionClick)
        },
        topBar = {
            AppTopBar(
                title = when(val state = uiState) {
                    is UiState.Success -> state.data.name
                    else -> ""
                },
                onBackClick = onBackCLick
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        when(val state = uiState) {
            UiState.Loading -> { }
            UiState.Empty -> {
                EmptySection(
                    modifier = Modifier.padding(innerPadding),
                    icon = AppIcons.AddToPlaylist,
                    title = stringResource(R.string.title_no_album_found)
                )
            }

            is UiState.Success -> {
                val playlist = state.data
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

                    if(songsInPlaylist.isEmpty()) {
                        item {
                            EmptySection(
                                message = stringResource(R.string.message_playlist_song_empty)
                            )
                        }
                    } else {
                        items(
                            count = songsInPlaylist.size,
                            key = { index -> songsInPlaylist[index].id }
                        ) { index ->
                            SongItem(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .fillMaxWidth(),
                                song = songsInPlaylist[index],
                                onSongClick = { song ->
                                    if(isConnect) {
                                        playlistDetailViewModel.play(
                                            queueSource = QueueSource.PLAYLIST,
                                            queue = songsInPlaylist,
                                            startSong = song,
                                            playlistId = playlist.id,
                                            playlistName = playlist.name
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
            }
        }

//        SongActionHost(
//            selectedSong = selectedSong,
//            playlists = playlists,
//            observeFavoriteSong = { songId ->
//                playlistDetailViewModel.isFavoriteSong(songId)
//            },
//            onDismissSong = { selectedSong = null },
//            onAddSongToFavorite = { songId ->
//                playlistDetailViewModel.addSongToFavorite(songId)
//            },
//            onRemoveSongFromFavorite = { songId ->
//                playlistDetailViewModel.removeSongFromFavorite(songId)
//            },
//            onCreatePlaylist = {playlistName ->
//                playlistDetailViewModel.createPlaylist(playlistName)
//            },
//            onAddSongToPlaylist = {playlistId, songId ->
//                playlistDetailViewModel.addSongToPlaylist(playlistId, songId)
//            },
//            onSongNavigationAction = onSongNavigationAction
//        )
    }
}