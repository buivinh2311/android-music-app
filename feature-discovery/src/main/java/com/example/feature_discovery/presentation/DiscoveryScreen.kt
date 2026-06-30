package com.example.feature_discovery.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
import com.example.core_ui.component.LoadingScreen
import com.example.core_ui.component.ViewAllButton
import com.example.core_ui.component.showToast
import com.example.core_ui.menu.AppBottomBarAction
import com.example.core_ui.state.UiState
import com.example.core_utils.util.AppUtil
import com.example.shared_presentation.presentation.ArtistItem
import com.example.shared_presentation.presentation.SongItem
import com.example.shared_presentation.presentation.SongLazyHorizontalGrid

@SuppressLint("LocalContextGetResourceValueCall")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoveryScreen(
    isConnect: Boolean,
    onSongOptionClick: (Song) -> Unit,
    onMoreArtistClick: () -> Unit,
    onArtistClick: (String) -> Unit,
    onForYouClick: () -> Unit,
    onMostListenedClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSongClick: (String) -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    val discoveryViewModel: DiscoveryViewModel = hiltViewModel()
    val uiState by discoveryViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            AppBottomBar(onBottomActionClick = onBottomActionClick)
        },
        topBar = {
            AppTopBar(
                title = stringResource(R.string.title_discovery),
                onSearchClick = onSearchClick
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        val isLoading = uiState.hotArtists is UiState.Loading ||
                uiState.forYouSongs is UiState.Loading ||
                uiState.mostHeardSongs is UiState.Loading
        if(isLoading) {
            LoadingScreen(modifier = Modifier.padding(innerPadding))
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background),
                contentPadding = PaddingValues(
                    top = AppDimens.Space.Lg,
                    bottom = AppDimens.Space.bottomSpace
                ),
            ) {
                item {
                    ViewAllButton(
                        title = stringResource(R.string.title_discovery_outstanding_singer),
                        onMoreClick = onMoreArtistClick
                    )
                    when(val state = uiState.hotArtists) {
                        UiState.Loading -> {

                        }

                        UiState.Empty -> {
                            EmptySection(
                                icon = AppIcons.Artist,
                                title = stringResource(R.string.title_no_artist_found)
                            )
                        }

                        is UiState.Success -> {
                            val hotArtists = state.data
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = AppDimens.Space.Lg),
                                horizontalArrangement = Arrangement.spacedBy(AppDimens.Space.Lg)
                            ) {
                                items(
                                    count = hotArtists.size,
                                    key = { index -> hotArtists[index].id }
                                ) { index ->
                                    ArtistItem(
                                        modifier = Modifier.width(150.dp),
                                        artist = hotArtists[index],
                                        titleMinLines = 2,
                                        onArtistClick = onArtistClick
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(AppDimens.Space.Sm))
                }

                item {
                    ViewAllButton(
                        title = stringResource(R.string.title_discovery_most_listened),
                        onMoreClick = onMostListenedClick
                    )
                    when(val state = uiState.mostHeardSongs) {
                        UiState.Loading -> {

                        }

                        UiState.Empty -> {
                            EmptySection(
                                icon = AppIcons.Song,
                                title = stringResource(R.string.title_no_song_found)
                            )
                        }

                        is UiState.Success -> {
                            val mostHeardSongs = state.data
                            SongLazyHorizontalGrid(
                                songs = mostHeardSongs,
                                rowWidth = 300.dp,
                                onSongClick = { song ->
                                    if(isConnect) {
                                        discoveryViewModel.play(
                                            queueSource = QueueSource.MOST_HEARD,
                                            queue = mostHeardSongs,
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
                    Spacer(modifier = Modifier.height(AppDimens.Space.Xl))
                }

                item {
                    ViewAllButton(
                        title = stringResource(R.string.title_discovery_for_your),
                        onMoreClick = onForYouClick
                    )
                }

                when(val state = uiState.forYouSongs) {
                    UiState.Loading -> {

                    }

                    UiState.Empty -> {
                        item {
                            EmptySection(
                                icon = AppIcons.Song,
                                title = stringResource(R.string.title_no_song_found)
                            )
                        }
                    }

                    is UiState.Success -> {
                        val forYouSongs = state.data
                        items(
                            count = minOf(forYouSongs.size, AppUtil.SECTION_PAGE_SIZE),
                            key = { index -> forYouSongs[index].id }
                        ) { index ->
                            SongItem(
                                modifier = Modifier
                                    .padding(horizontal = AppDimens.Space.Xs)
                                    .fillMaxWidth(),
                                song = forYouSongs[index],
                                onSongClick = { song ->
                                    if(isConnect) {
                                        discoveryViewModel.play(
                                            queueSource = QueueSource.FOR_YOU,
                                            queue = forYouSongs,
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
}