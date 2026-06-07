package com.example.feature_album.presentation.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.core_model.Album
import com.example.core_model.DisplaySong
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_ui.data.AppBottomBarAction
import com.example.core_ui.data.SongOptionItem
import com.example.core_ui.ui.AppBottomBar
import com.example.core_ui.ui.AppTopBar
import com.example.core_ui.ui.SongItem
import com.example.core_ui.ui.SongOptionBottomSheet
import com.example.feature_album.presentation.component.AlbumAction
import com.example.feature_album.presentation.component.AlbumInformation
import com.example.feature_album.presentation.viewmodel.AlbumDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumDetailScreen(
    albumName: String,
    onSongClick: (String) -> Unit,
    onBackCLick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit,
    onSongOptionClick: (SongOptionItem) -> Unit,

) {
    var selectedSong: DisplaySong? by remember {
        mutableStateOf(null)
    }
    val albumDetailViewModel: AlbumDetailViewModel = hiltViewModel()
    val uiState by albumDetailViewModel.uiState.collectAsState()
    LaunchedEffect(albumName) {
        albumDetailViewModel.loadAlbumDetail(albumName)
    }
    val songs = uiState.songs
    val album = uiState.album ?: Album(0, albumName, "", songs.size)
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AppBottomBar(onBottomActionClick = onBottomActionClick)
        },
        topBar = {
            AppTopBar(
                title = album.name,
                onBackClick = onBackCLick
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
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    AlbumInformation(album)
                    Spacer(modifier = Modifier.height(AppDimens.Space.Sm))
                    AlbumAction()
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