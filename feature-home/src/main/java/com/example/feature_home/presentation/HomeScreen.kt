package com.example.feature_home.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.shared_presentation.presentation.AlbumItem
import com.example.shared_presentation.presentation.SongItem

@SuppressLint("LocalContextGetResourceValueCall")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    isConnect: Boolean,
    onSongOptionClick: (Song) -> Unit,
    onMoreAlbumClick: () -> Unit,
    onAlbumClick: (String) -> Unit,
    onRecommendedClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSongClick: (String) -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.title_home),
                onSearchClick = onSearchClick
            )
        },
        bottomBar = {
            AppBottomBar(onBottomActionClick = onBottomActionClick)
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        val isLoading = uiState.hotAlbums is UiState.Loading ||
                uiState.recommendedSongs is UiState.Loading
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
                )
            ) {
                item {
                    ViewAllButton(
                        title = stringResource(R.string.title_home_popular_album),
                        onMoreClick = onMoreAlbumClick
                    )
                    when(val state = uiState.hotAlbums) {
                        UiState.Loading -> {

                        }

                        UiState.Empty -> {
                            EmptySection(
                                modifier = Modifier.padding(innerPadding),
                                icon = AppIcons.Album,
                                title = stringResource(R.string.title_no_album_found)
                            )
                        }

                        is UiState.Success -> {
                            val hotAlbums = state.data
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = AppDimens.Space.Lg),
                                horizontalArrangement = Arrangement.spacedBy(AppDimens.Space.Lg)
                            ) {
                                items(
                                    count = AppUtil.SECTION_PAGE_SIZE,
                                    key = { index -> hotAlbums[index].id }
                                ) { index ->
                                    AlbumItem(
                                        modifier = Modifier.width(150.dp),
                                        album = hotAlbums[index],
                                        titleMinLines = 2,
                                        onAlbumClick = onAlbumClick
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(AppDimens.Space.Lg))
                }

                item {
                    ViewAllButton(
                        title = stringResource(R.string.title_home_recommended_song),
                        onMoreClick = onRecommendedClick
                    )
                }

                when(val state = uiState.recommendedSongs) {
                    UiState.Loading -> {

                    }

                    UiState.Empty -> {
                        item {
                            EmptySection(
                                modifier = Modifier.padding(innerPadding),
                                icon = AppIcons.Song,
                                title = stringResource(R.string.title_no_song_found)
                            )
                        }
                    }

                    is UiState.Success -> {
                        val recommendedSongs = state.data
                        items(
                            count = minOf(recommendedSongs.size, AppUtil.SECTION_PAGE_SIZE),
                            key = { index -> recommendedSongs[index].id }
                        ) { index ->
                            SongItem(
                                modifier = Modifier.padding(horizontal = AppDimens.Space.Xs),
                                song = recommendedSongs[index],
                                onSongClick = { song ->
                                    if(isConnect) {
                                        homeViewModel.play(
                                            queueSource = QueueSource.RECOMMENDED,
                                            queue = recommendedSongs,
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