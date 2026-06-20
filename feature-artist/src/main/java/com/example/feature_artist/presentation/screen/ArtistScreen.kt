package com.example.feature_artist.presentation.screen

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
import com.example.core_resources.ui.icon.AppIcons
import com.example.core_ui.component.AppBottomBar
import com.example.core_ui.component.AppTopBar
import com.example.core_ui.component.EmptyScreen
import com.example.core_ui.component.LoadingScreen
import com.example.shared_presentation.presentation.ArtistItem
import com.example.core_ui.component.showToast
import com.example.core_ui.menu.AppBottomBarAction
import com.example.core_ui.state.UiState
import com.example.feature_artist.presentation.viewmodel.ArtistViewModel
import com.example.shared_presentation.presentation.MiniPlayer

@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun ArtistScreen(
    onArtistClick: (String) -> Unit,
    onMiniPlayerClick: (String) -> Unit,
    onBackCLick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    val artistViewModel: ArtistViewModel = hiltViewModel()
    val uiState by artistViewModel.uiState.collectAsStateWithLifecycle()
    val playbackState by artistViewModel.playbackState
        .collectAsStateWithLifecycle()
    val isCurrentFavoriteSong by artistViewModel.currentFavoriteSong
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
                title = stringResource(R.string.title_artist_hot),
                onBackClick = onBackCLick
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        when(val state = uiState) {
            UiState.Loading -> {
                LoadingScreen()
            }

            UiState.Empty -> {
                EmptyScreen(
                    icon = AppIcons.Artist,
                    title = stringResource(R.string.title_no_artist_found)
                )
            }

            is UiState.Success -> {
                val artists = state.data
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
                        count = artists.size,
                        key = { index -> artists[index].id }
                    ) { index ->
                        ArtistItem(
                            modifier = Modifier.fillMaxWidth(),
                            artist = artists[index],
                            titleMinLines = 1,
                            onArtistClick = onArtistClick
                        )
                    }
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
                            artistViewModel.removeSongFromFavorite(currentSong.id)
                            showToast(
                                context,
                                message = context.getString(
                                    R.string.remove_song_from_favorite_success,
                                    currentSong.title
                                )
                            )
                        } else {
                            artistViewModel.addSongToFavorite(currentSong.id)
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
                            artistViewModel.pause()
                        } else {
                            artistViewModel.resume()
                        }
                    },
                    onNextClick = {
                        artistViewModel.skipNext()
                    }
                )
            }
        }
    }
}