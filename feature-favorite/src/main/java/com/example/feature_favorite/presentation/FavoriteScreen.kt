package com.example.feature_favorite.presentation

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
fun FavoriteScreen(
    isConnect: Boolean,
    selectedAction: AppBottomBarAction,
    onSongClick: (String) -> Unit,
    onSongOptionClick: (Song) -> Unit,
    onBackCLick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    val favoriteViewModel: FavoriteViewModel = hiltViewModel()
    val uiState by favoriteViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AppBottomBar(
                selectedAction = selectedAction,
                onBottomActionClick = onBottomActionClick
            )
        },
        topBar = {
            AppTopBar(
                title = stringResource(R.string.title_favorite_song),
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
                        icon = AppIcons.Favorite,
                        title = stringResource(R.string.title_favorite_song_empty),
                        message = stringResource(R.string.message_favorite_song_empty)
                    )
                }

                is UiState.Success -> {
                    val favoriteSongs = state.data
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
                                text = stringResource(R.string.label_favorite),
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Spacer(modifier = Modifier.height(AppDimens.Space.Xs))
                            Text(
                                text = favoriteSongs.size.toString() + stringResource(R.string.text_song),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Spacer(modifier = Modifier.height(AppDimens.Space.Xl))
                            Button(
                                onClick = {
                                    if(isConnect) {
                                        val startSong = favoriteSongs[0]
                                        favoriteViewModel.play(
                                            queueSource = QueueSource.FAVORITE,
                                            queue = favoriteSongs,
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
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                            Spacer(modifier = Modifier.height(AppDimens.Space.Xl))
                        }

                        items(
                            count = favoriteSongs.size,
                            key = { index -> favoriteSongs[index].id }
                        ) { index ->
                            SongItem(
                                modifier = Modifier.padding(horizontal = 4.dp),
                                song = favoriteSongs[index],
                                onSongClick = { song ->
                                    if(isConnect) {
                                        favoriteViewModel.play(
                                            queueSource = QueueSource.FAVORITE,
                                            queue = favoriteSongs,
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