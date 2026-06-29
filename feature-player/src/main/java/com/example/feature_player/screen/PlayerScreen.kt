package com.example.feature_player.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.core_model.Song
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_ui.component.LoadingScreen
import com.example.core_ui.component.showToast
import com.example.feature_player.component.PlayerArtWork
import com.example.feature_player.component.PlayerControls
import com.example.feature_player.component.PlayerExtraAction
import com.example.feature_player.component.PlayerInfo
import com.example.feature_player.component.PlayerProgress
import com.example.feature_player.component.PlayerTopBar
import com.example.feature_player.viewmodel.PlayerViewModel
import kotlinx.coroutines.flow.Flow

@SuppressLint("LocalContextGetResourceValueCall")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    songId: String,
    observeDownloadSong: (String) -> Flow<Boolean>,
    onSongOptionClick: (Song) -> Unit,
    onBackClick: () -> Unit,
    onViewArtistClick: (String) -> Unit,
    onDownloadClick: (Song) -> Unit,
    onViewQueueClick: () -> Unit,
) {
    val playerViewModel: PlayerViewModel = hiltViewModel()
    val uiState by playerViewModel.uiState.collectAsStateWithLifecycle()
    val playbackState by playerViewModel.playbackState
        .collectAsStateWithLifecycle()
    val isCurrentFavoriteSong by playerViewModel.currentSongFavorite
        .collectAsStateWithLifecycle()
    val currentSong = playbackState.playQueue.getOrNull(playbackState.currentIndex)
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            model = currentSong?.artworkUrl,
            placeholder = painterResource(R.drawable.logo),
            error = painterResource(R.drawable.logo),
            contentDescription = "Song artwork",
            modifier = Modifier
                .fillMaxSize()
                .blur(80.dp),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        )

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent
        ) { innerPadding ->
            if (uiState.isLoading) {
                LoadingScreen(modifier = Modifier.padding(innerPadding))
            } else {
                currentSong?.let {
                    val isDownloadSong by observeDownloadSong(currentSong.id)
                        .collectAsStateWithLifecycle(false)
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(AppDimens.Space.Lg),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        PlayerTopBar(
                            queueSource = playbackState.queueSource,
                            song = currentSong,
                            sourceName = playbackState.sourceName ?: "_",
                            onClick = {
                                onSongOptionClick(currentSong)
                            },
                            onBackClick = onBackClick
                        )
                        Spacer(modifier = Modifier.height(40.dp))

                        PlayerArtWork(
                            artworkUrl = currentSong.artworkUrl,
                            isPlaying = playbackState.isPlaying,
                            onArtworkClick = {
                                onSongOptionClick(currentSong)
                            }
                        )
                        Spacer(modifier = Modifier.height(48.dp))

                        PlayerInfo(
                            title = currentSong.title,
                            artist = currentSong.artist,
                            isFavoriteSong = isCurrentFavoriteSong,
                            onFavoriteClick = {
                                if (isCurrentFavoriteSong) {
                                    playerViewModel.removeSongToFavorite(currentSong.id)
                                    showToast(
                                        context,
                                        message = context.getString(
                                            R.string.remove_song_from_favorite_success,
                                            currentSong.title
                                        )
                                    )
                                } else {
                                    playerViewModel.addSongToFavorite(currentSong.id)
                                    showToast(
                                        context,
                                        message = context.getString(
                                            R.string.add_song_to_favorite_success,
                                            currentSong.title
                                        )
                                    )
                                }
                            },
                            onShareClick = {
                                showToast(
                                    context,
                                    message = context.getString(
                                        R.string.currently_under_development
                                    )
                                )
                            }
                        )
                        Spacer(modifier = Modifier.height(40.dp))

                        PlayerProgress(
                            currentPosition = playbackState.currentPosition,
                            duration = playbackState.duration,
                            onPositionChange = {
                                playerViewModel.seekTo(it.toLong())
                            }
                        )
                        Spacer(modifier = Modifier.height(AppDimens.Space.Xl))

                        PlayerControls(
                            isPlaying = playbackState.isPlaying,
                            onPlayClick = {
                                if (playbackState.isPlaying) {
                                    playerViewModel.pause()
                                } else {
                                    playerViewModel.resume()
                                }
                            },
                            onPreviousClick = {
                                playerViewModel.skipPrevious()
                            },
                            onNextClick = {
                                playerViewModel.skipNext()
                            },
                            isShuffleEnabled = playbackState.isShuffleEnabled,
                            onShuffleClick = {
                                playerViewModel.toggleShuffle()
                            },
                            repeatMode = playbackState.repeatMode,
                            onRepeatModeClick = {
                                playerViewModel.changeRepeatMode()
                            }
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        PlayerExtraAction(
                            isDownloadSong = isDownloadSong,
                            onViewComment = {
                                showToast(
                                    context,
                                    message = context.getString(
                                        R.string.currently_under_development
                                    )
                                )
                            },
                            onDownloadClick = {
                                if(isDownloadSong) {
                                    showToast(
                                        context,
                                        message = context.getString(
                                            R.string.text_downloading
                                        )
                                    )
                                } else {
                                    onDownloadClick(currentSong)
                                    showToast(
                                        context,
                                        message = context.getString(
                                            R.string.text_downloading
                                        )
                                    )
                                }
                            },
                            onViewArtistClick = {
                                onViewArtistClick(currentSong.artist)
                            },
                            onViewQueueClick = onViewQueueClick
                        )
                    }
                }
            }
        }
    }
}
