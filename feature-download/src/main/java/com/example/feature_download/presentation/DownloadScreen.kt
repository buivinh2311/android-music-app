package com.example.feature_download.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.core_ui.component.EmptyScreen
import com.example.core_ui.component.LoadingScreen
import com.example.core_ui.component.showToast
import com.example.core_ui.menu.AppBottomBarAction
import com.example.core_ui.state.UiState
import com.example.shared_presentation.presentation.SongItem

@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun DownloadScreen(
    onSongClick: (String) -> Unit,
    onSongOptionClick: (Song) -> Unit,
    onBackClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    val downloadViewModel: DownloadViewModel = hiltViewModel()
    val uiState by downloadViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AppBottomBar(onBottomActionClick = onBottomActionClick)
        },
        topBar = {
            AppTopBar(
                title = stringResource(R.string.title_download),
                onBackClick = onBackClick
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        when(val state = uiState) {
            UiState.Loading -> {
                LoadingScreen(
                    modifier = Modifier.padding(innerPadding)
                )
            }

            UiState.Empty -> {
                EmptyScreen(
                    modifier = Modifier.padding(innerPadding),
                    icon = AppIcons.Download,
                    title = stringResource(R.string.title_download_song_empty),
                    message = stringResource(R.string.message_download_song_empty)
                )
            }

            is UiState.Success -> {
                val downloadSongs = state.data
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .background(MaterialTheme.colorScheme.background),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(
                        top = AppDimens.Space.Lg,
                        bottom = AppDimens.Space.bottomSpace
                    )
                ) {
                    item {
                        Text(
                            text = stringResource(R.string.label_download),
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(AppDimens.Space.Xs))
                        Text(
                            text = downloadSongs.size.toString() + stringResource(R.string.text_song),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(AppDimens.Space.Xl))
                        Button(
                            onClick = {},
                            modifier = Modifier.width(150.dp),
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.White,
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(
                                text = stringResource(R.string.action_play_music),
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        Spacer(modifier = Modifier.height(AppDimens.Space.Xl))
                    }

                    items(
                        count = downloadSongs.size,
                        key = { index -> downloadSongs[index].id }
                    ) { index ->
                        SongItem(
                            modifier = Modifier.padding(horizontal = 4.dp),
                            song = downloadSongs[index],
                            onSongClick = { song ->
                                downloadViewModel.play(
                                    queueSource = QueueSource.DOWNLOAD,
                                    queue = downloadSongs,
                                    startSong = song
                                )
                                onSongClick(song.id)
                            },
                            onSongOptionClick = onSongOptionClick
                        )
                    }
                }
            }
        }
    }
}