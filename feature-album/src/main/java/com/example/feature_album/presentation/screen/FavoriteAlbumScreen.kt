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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_ui.component.AlbumItem
import com.example.core_ui.component.AppBottomBar
import com.example.core_ui.component.AppTopBar
import com.example.core_ui.component.showToast
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_album.presentation.viewmodel.FavoriteAlbumViewModel
import com.example.shared_presentation.presentation.MiniPlayer

@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun FavoriteAlbumScreen(
    onAlbumClick: (String) -> Unit,
    onMiniPlayerClick: (String) -> Unit,
    onBackClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    val favoriteAlbumViewModel: FavoriteAlbumViewModel = hiltViewModel()
    val favoriteAlbums by favoriteAlbumViewModel.favoriteAlbums
        .collectAsStateWithLifecycle(emptyList())

    val playbackState by favoriteAlbumViewModel.playbackState
        .collectAsStateWithLifecycle()

    val isCurrentFavoriteSong by favoriteAlbumViewModel.currentFavoriteSong
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
                title = stringResource(R.string.action_library_album),
                onBackClick = onBackClick
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
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
                count = favoriteAlbums.size,
                key = {index -> favoriteAlbums[index].id}
            ) { index ->
                val album = favoriteAlbums[index]
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
                            favoriteAlbumViewModel.removeSongFromFavorite(currentSong.id)
                            showToast(
                                context,
                                message = context.getString(
                                    R.string.remove_song_from_favorite_success,
                                    currentSong.title
                                )
                            )
                        } else {
                            favoriteAlbumViewModel.addSongToFavorite(currentSong.id)
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
                            favoriteAlbumViewModel.pause()
                        } else {
                            favoriteAlbumViewModel.resume()
                        }
                    },
                    onNextClick = {
                        favoriteAlbumViewModel.skipNext()
                    }
                )
            }
        }
    }
}