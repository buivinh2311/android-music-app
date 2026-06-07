package com.example.feature_home.presentation.screen

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
import com.example.core_ui.ui.AlbumItem
import com.example.core_ui.ui.AppBottomBar
import com.example.core_ui.ui.AppTopBar
import com.example.core_ui.ui.SongItem
import com.example.core_ui.ui.SongOptionBottomSheet
import com.example.core_ui.ui.ViewAllButton
import com.example.feature_home.presentation.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onMoreAlbumClick: () -> Unit,
    onAlbumClick: (String) -> Unit,
    onRecommendedClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSongClick: (String) -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit,
    onSongOptionClick: (SongOptionItem) -> Unit
) {
    var selectedSong: DisplaySong? by remember {
        mutableStateOf(null)
    }
    val homeViewModel: HomeViewModel = hiltViewModel()
    val uiState by homeViewModel.uiState.collectAsState()
    val hotAlbums = uiState.hotAlbums
    val recommendedSongs = uiState.recommendedSongs
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AppBottomBar(onBottomActionClick = onBottomActionClick)
        },
        topBar = {
            AppTopBar(title = stringResource(R.string.title_home))
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
                        title = stringResource(R.string.title_home_popular_album),
                        onMoreClick = onMoreAlbumClick
                    )
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = AppDimens.Space.Lg),
                        horizontalArrangement = Arrangement.spacedBy(AppDimens.Space.Lg)
                    ) {
                        items(
                            count = hotAlbums.size,
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
                    Spacer(modifier = Modifier.height(AppDimens.Space.Lg))
                    ViewAllButton(
                        title = stringResource(R.string.title_home_recommended_song),
                        onMoreClick = onRecommendedClick
                    )
                }

                items(
                    count = recommendedSongs.size,
                    key = { index -> recommendedSongs[index].id }
                ) { index ->
                    SongItem(
                        modifier = Modifier.padding(horizontal = AppDimens.Space.Xs),
                        song = recommendedSongs[index],
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