package com.example.feature_player.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.core_model.Song
import com.example.core_model.playback.QueueSource
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_resources.ui.icon.AppIcons
import com.example.core_ui.component.AppIconButton
import com.example.core_ui.component.EmptyScreen
import com.example.core_ui.component.LoadingScreen
import com.example.core_ui.component.showToast
import com.example.core_ui.state.UiState
import com.example.feature_player.viewmodel.QueueViewModel

@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun QueueScreen(
    isConnect: Boolean,
    onSongOptionClick: (Song) -> Unit,
    onBackClick: () -> Unit
) {
    val queueViewModel: QueueViewModel = hiltViewModel()
    val uiState by queueViewModel.uiState.collectAsStateWithLifecycle()
    val playbackState by queueViewModel.playbackState.collectAsStateWithLifecycle()
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
            containerColor = Color.Transparent,
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(
                            start = AppDimens.Space.Lg,
                            top = AppDimens.Space.Md,
                            bottom = AppDimens.Space.Sm
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AppIconButton(
                        painter = AppIcons.Back,
                        contentDescription = stringResource(R.string.action_down),
                        iconSize = AppDimens.Icon.Sm,
                        rippleRadius = AppDimens.Ripple.Sm,
                        tint = Color.White,
                        rippleColor = Color.White
                    ) {
                        onBackClick()
                    }
                    Spacer(modifier = Modifier.width(AppDimens.Space.Lg))

                    Text(
                        text = stringResource(R.string.title_queue),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                }
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(
                    vertical = AppDimens.Space.Lg
                )
            ) {
                when (val state = uiState) {
                    UiState.Loading -> {
                        item {
                            LoadingScreen(modifier = Modifier.padding(innerPadding))
                        }
                    }

                    UiState.Empty -> {
                        item {
                            EmptyScreen(
                                modifier = Modifier.padding(innerPadding),
                                icon = AppIcons.Song,
                                title = stringResource(R.string.title_no_song_found)
                            )
                        }
                    }

                    is UiState.Success -> {
                        val queue = state.data
                        items(
                            count = queue.size,
                            key = { index -> queue[index].id }
                        ) { index ->
                            val song = queue[index]
                            Surface(
                                onClick = {
                                    if (isConnect || playbackState.queueSource == QueueSource.DOWNLOAD) {
                                        queueViewModel.seekTo(index, 0L)
                                    } else {
                                        showToast(
                                            context,
                                            message = context.getString(
                                                R.string.no_internet_message
                                            )
                                        )
                                    }
                                },
                                shape = RoundedCornerShape(AppDimens.Radius.Sm),
                                color = Color.Transparent,
                                modifier = if (index == playbackState.currentIndex)
                                    Modifier
                                        .padding(horizontal = AppDimens.Space.Xs)
                                        .clip(RoundedCornerShape(AppDimens.Radius.Sm))
                                        .background(color = Color.White.copy(alpha = 0.1f))
                                        .height(AppDimens.Layout.SongItemHeight)
                                else Modifier
                                    .padding(horizontal = AppDimens.Space.Xs)
                                    .height(AppDimens.Layout.SongItemHeight)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(
                                            start = AppDimens.Space.Md,
                                            top = AppDimens.Space.Sm,
                                            end = AppDimens.Space.Sm,
                                            bottom = AppDimens.Space.Sm
                                        ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    AsyncImage(
                                        model = song.artworkUrl,
                                        contentDescription = null,
                                        placeholder = painterResource(R.drawable.logo),
                                        error = painterResource(R.drawable.logo),
                                        modifier = Modifier
                                            .size(AppDimens.ImageSize.Md)
                                            .clip(shape = RoundedCornerShape(AppDimens.Radius.Sm)),
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(modifier = Modifier.width(AppDimens.Space.Sm))

                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = song.title,
                                            style = MaterialTheme.typography.titleSmall,
                                            color = Color.White,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                        )
                                        Spacer(modifier = Modifier.height(AppDimens.Space.Xs))
                                        Text(
                                            text = song.artist,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = Color.White.copy(alpha = 0.7f),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(AppDimens.Space.Xs))

                                    AppIconButton(
                                        painter = AppIcons.More,
                                        contentDescription = stringResource(R.string.action_view_more),
                                        iconSize = AppDimens.Icon.Sm,
                                        rippleRadius = AppDimens.Ripple.Sm,
                                        tint = Color.White,
                                        rippleColor = Color.White
                                    ) {
                                        onSongOptionClick(song)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}