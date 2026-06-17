package com.example.feature_playlist.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_ui.menu.AppBottomBarAction
import com.example.core_ui.component.AppBottomBar
import com.example.core_ui.component.AppTopBar
import com.example.core_ui.component.PlaylistItem
import com.example.core_ui.component.showToast
import com.example.feature_playlist.presentation.viewmodel.PlaylistViewModel
import com.example.shared_presentation.presentation.MiniPlayer

@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun PlaylistScreen(
    onPlaylistClick: (Int) -> Unit,
    onMiniPlayerClick: (String) -> Unit,
    onBackClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    val playlistViewModel: PlaylistViewModel = hiltViewModel()
    val uiState by playlistViewModel.uiState.collectAsStateWithLifecycle()
    val playbackState by playlistViewModel.playbackState
        .collectAsStateWithLifecycle()
    val isCurrentFavoriteSong by playlistViewModel.currentFavoriteSong
        .collectAsStateWithLifecycle()
    val currentSong = playbackState.queue.getOrNull(playbackState.currentIndex)
    val context = LocalContext.current
    val playlists = uiState.playlists
    
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AppBottomBar(onBottomActionClick = onBottomActionClick)
        },
        topBar = {
            AppTopBar(
                title = stringResource(R.string.title_library_playlist),
                onBackClick = onBackClick
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
            ),
            verticalArrangement = Arrangement.spacedBy(AppDimens.Space.Xs)
        ) {
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
                            playlistViewModel.removeSongFromFavorite(currentSong.id)
                            showToast(
                                context,
                                message = context.getString(
                                    R.string.remove_song_from_favorite_success,
                                    currentSong.title
                                )
                            )
                        } else {
                            playlistViewModel.addSongToFavorite(currentSong.id)
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
                            playlistViewModel.pause()
                        } else {
                            playlistViewModel.resume()
                        }
                    },
                    onNextClick = {
                        playlistViewModel.skipNext()
                    }
                )
            }
        }
    }
}