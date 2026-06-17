package com.example.feature_album.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import com.example.core_ui.component.AlbumItem
import com.example.core_ui.component.AppBottomBar
import com.example.core_ui.component.AppTopBar
import com.example.core_ui.component.showToast
import com.example.feature_album.presentation.viewmodel.AlbumViewModel
import com.example.shared_presentation.presentation.MiniPlayer

@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun AlbumScreen(
    onAlbumClick: (String) -> Unit,
    onMiniPlayerClick: (String) -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit,
    onBackClick: () -> Unit
) {
    val albumViewModel: AlbumViewModel = hiltViewModel()
    val uiState by albumViewModel.uiState.collectAsStateWithLifecycle()
    val albums = uiState.albums
    val playbackState by albumViewModel.playbackState
        .collectAsStateWithLifecycle()
    val isCurrentFavoriteSong by albumViewModel.currentFavoriteSong
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
                title = stringResource(R.string.titlt_album_hot),
                onBackClick = onBackClick
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        if(albums.isNotEmpty()) {
            LazyVerticalGrid(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(
                    top = AppDimens.Space.Lg,
                    start = AppDimens.Space.Lg,
                    end = AppDimens.Space.Lg,
                    bottom = AppDimens.Space.bottomSpace
                ),
                verticalArrangement = Arrangement.spacedBy(AppDimens.Space.Xl),
                horizontalArrangement = Arrangement.spacedBy(AppDimens.Space.Md)
            ) {
                items(
                    count = albums.size,
                    key = {index -> albums[index].id}
                ) { index ->
                    val album = albums[index]
                    AlbumItem(
                        modifier = Modifier.fillMaxWidth(),
                        album = album,
                        titleMinLines = 1,
                        onAlbumClick = onAlbumClick
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
                                albumViewModel.removeSongFromFavorite(currentSong.id)
                                showToast(
                                    context,
                                    message = context.getString(
                                        R.string.remove_song_from_favorite_success,
                                        currentSong.title
                                    )
                                )
                            } else {
                                albumViewModel.addSongToFavorite(currentSong.id)
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
                                albumViewModel.pause()
                            } else {
                                albumViewModel.resume()
                            }
                        },
                        onNextClick = {
                            albumViewModel.skipNext()
                        }
                    )
                }
            }
        } else if(uiState.isLoading) {

        }
    }
}