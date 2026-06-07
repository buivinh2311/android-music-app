package com.example.feature_discovery.presentation.screen

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.core_model.DisplaySong
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_ui.data.AppBottomBarAction
import com.example.core_ui.data.SongOptionItem
import com.example.core_ui.ui.AppBottomBar
import com.example.core_ui.ui.AppTopBar
import com.example.core_ui.ui.ArtistItem
import com.example.core_ui.ui.SongItem
import com.example.core_ui.ui.SongLazyHorizontalGrid
import com.example.core_ui.ui.SongOptionBottomSheet
import com.example.core_ui.ui.ViewAllButton
import com.example.feature_discovery.presentation.viewmodel.DiscoveryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoveryScreen(
    onMoreArtistClick: () -> Unit,
    onArtistClick: (String) -> Unit,
    onForYouClick: () -> Unit,
    onMostListenedClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSongClick: (String) -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit,
    onSongOptionClick: (SongOptionItem) -> Unit
) {
    var selectedSong: DisplaySong? by remember {
        mutableStateOf(null)
    }
    val discoveryViewModel: DiscoveryViewModel = hiltViewModel()
    val uiState by discoveryViewModel.uiState.collectAsState()
    val hotArtists = uiState.hotArtists
    val mostHeardSongs = uiState.mostHeardSongs
    val forYouSongs = uiState.forYouSongs
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            AppBottomBar(onBottomActionClick = onBottomActionClick)
        },
        topBar = {
            AppTopBar(
                title = stringResource(R.string.title_discovery)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        if(uiState.isLoading) {

        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background),
                contentPadding = PaddingValues(
                    vertical = AppDimens.Space.Lg
                )
            ) {
                item {
                    ViewAllButton(
                        title = stringResource(R.string.title_discovery_outstanding_singer),
                        onMoreClick = onMoreArtistClick
                    )
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
                    Spacer(modifier = Modifier.height(AppDimens.Space.Sm))
                    ViewAllButton(
                        title = stringResource(R.string.title_discovery_most_listened),
                        onMoreClick = onMostListenedClick
                    )
                    SongLazyHorizontalGrid(
                        songs = mostHeardSongs,
                        rowWidth = 300.dp,
                        onSongClick = onSongClick,
                        onMoreClick = { song ->
                            selectedSong = song
                        }
                    )
                    Spacer(modifier = Modifier.height(AppDimens.Space.Xl))
                    ViewAllButton(
                        title = stringResource(R.string.title_discovery_for_your),
                        onMoreClick = onForYouClick
                    )
                }

                items(
                    count = forYouSongs.size,
                    key = { index -> forYouSongs[index].id }
                ) { index ->
                    SongItem(
                        modifier = Modifier
                            .padding(horizontal = AppDimens.Space.Xs)
                            .fillMaxWidth(),
                        song = forYouSongs[index],
                        onSongClick = onSongClick,
                        onMoreClick = { song ->
                            selectedSong = song
                        }
                    )
                }
            }

            selectedSong?.let {
                SongOptionBottomSheet(
                    song = it,
                    onDismiss = { selectedSong = null },
                    onSongOptionClick = onSongOptionClick
                )
            }
        }
    }
}