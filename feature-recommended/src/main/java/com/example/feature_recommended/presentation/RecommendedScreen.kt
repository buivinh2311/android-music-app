package com.example.feature_recommended.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
fun RecommendedScreen(
    isConnect: Boolean,
    onSongClick: (String) -> Unit,
    onSongOptionClick: (Song) -> Unit,
    onBackCLick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    val recommendedViewModel: RecommendedViewModel = hiltViewModel()
    val uiState by recommendedViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AppBottomBar(onBottomActionClick = onBottomActionClick)
        },
        topBar = {
            AppTopBar(
                title = stringResource(R.string.title_home_recommended_song),
                onBackClick = onBackCLick
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
                    icon = AppIcons.Song,
                    title = stringResource(R.string.title_no_song_found)
                )
            }

            is UiState.Success -> {
                val songs = state.data
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
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = {
                                    if(isConnect) {
                                        val startSong = songs[0]
                                        recommendedViewModel.play(
                                            queueSource = QueueSource.RECOMMENDED,
                                            queue = songs,
                                            startSong = startSong
                                        )
                                        onSongClick(startSong.id)
                                    } else {
                                        showToast(
                                            context,
                                            message = context.getString(
                                                R.string.no_internet_message
                                            )
                                        )
                                    }
                                },
                                modifier = Modifier.width(150.dp),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Color.White,
                                    containerColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Text(
                                    text = stringResource(R.string.action_play_music),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.White
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(AppDimens.Space.Xl))
                    }

                    items(
                        count = songs.size,
                        key = { index -> songs[index].id }
                    ) { index ->
                        SongItem(
                            modifier = Modifier
                                .padding(horizontal = AppDimens.Space.Xs)
                                .fillMaxWidth(),
                            song = songs[index],
                            onSongClick = { song ->
                                if(isConnect) {
                                    recommendedViewModel.play(
                                        queueSource = QueueSource.RECOMMENDED,
                                        queue = songs,
                                        startSong = song
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
}