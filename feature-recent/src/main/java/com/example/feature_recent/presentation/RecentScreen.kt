package com.example.feature_recent.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.core_model.DisplaySong
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_ui.component.AppBottomBar
import com.example.core_ui.component.AppTopBar
import com.example.core_ui.component.SongItem
import com.example.core_ui.menu.AppBottomBarAction
import com.example.shared_presentation.model.SongOptionItem
import com.example.shared_presentation.presentation.SongActionHost

@Composable
fun RecentScreen(
    onSongClick: (String) -> Unit,
    onBackCLick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit,
    onSongNavigationAction: (SongOptionItem) -> Unit
) {
    var selectedSong: DisplaySong? by remember {
        mutableStateOf(null)
    }
    val recentViewModel: RecentViewModel = hiltViewModel()
    val playlists by recentViewModel.playlists.collectAsState()
    val recentSongs by recentViewModel.recentSongs.collectAsState()

    Scaffold(
    modifier = Modifier.fillMaxSize(),
    bottomBar = {
        AppBottomBar(onBottomActionClick = onBottomActionClick)
    },
    topBar = {
        AppTopBar(
            title = stringResource(R.string.title_library_heard_recently),
            onBackClick = onBackCLick
        )
    },
    containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(AppDimens.Space.Xl))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {},
                        modifier = Modifier.width(200.dp),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.action_play_random),
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.height(AppDimens.Space.Xl))
            }

            items(
                count = recentSongs.size,
                key = { index -> recentSongs[index].id }
            ) { index ->
                SongItem(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    song = recentSongs[index],
                    onSongClick = onSongClick,
                    onMoreClick = { song ->
                        selectedSong = song
                    }
                )
            }
        }

        SongActionHost(
            selectedSong = selectedSong,
            playlists = playlists,
            observeFavoriteSong = { songId ->
                recentViewModel.isFavoriteSong(songId)
            },
            onDismissSong = { selectedSong = null },
            onAddSongToFavorite = { songId ->
                recentViewModel.addSongToFavorite(songId)
            },
            onRemoveSongFromFavorite = { songId ->
                recentViewModel.removeSongToFavorite(songId)
            },
            onCreatePlaylist = {playlistName ->
                recentViewModel.createPlaylist(playlistName)
            },
            onAddSongToPlaylist = {playlistId, songId ->
                recentViewModel.addSongToPlaylist(playlistId, songId)
            },
            onSongNavigationAction = onSongNavigationAction
        )
    }
}