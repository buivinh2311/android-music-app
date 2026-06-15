package com.example.feature_favorite.presentation

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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_model.Song
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_ui.component.AppBottomBar
import com.example.core_ui.component.AppTopBar
import com.example.core_ui.component.SongItem
import com.example.core_ui.menu.AppBottomBarAction
import com.example.shared_presentation.model.SongOptionItem
import com.example.shared_presentation.presentation.SongActionHost

@Composable
fun FavoriteScreen(
    onSongClick: (String) -> Unit,
    onBackCLick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit,
    onSongNavigationAction: (SongOptionItem) -> Unit
) {
    var selectedSong: Song? by remember {
        mutableStateOf(null)
    }
    val favoriteViewModel: FavoriteViewModel = hiltViewModel()
    val favoriteSongs by favoriteViewModel.songs
        .collectAsStateWithLifecycle(emptyList())
    val playlists by favoriteViewModel.playlists
        .collectAsStateWithLifecycle(emptyList())
    val queueSource = stringResource(R.string.title_favorite_song)
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AppBottomBar(onBottomActionClick = onBottomActionClick)
        },
        topBar = {
            AppTopBar(
                title = stringResource(R.string.title_favorite_song),
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
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(
                vertical = AppDimens.Space.Xl
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
                count = favoriteSongs.size,
                key = { index -> favoriteSongs[index].id }
            ) { index ->
                SongItem(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    song = favoriteSongs[index],
                    onSongClick = { song ->
                        favoriteViewModel.play(
                            queueSource = queueSource,
                            queue = favoriteSongs,
                            startSong = song
                        )
                        onSongClick(song.id)
                    },
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
                favoriteViewModel.isFavoriteSong(songId)
            },
            onDismissSong = { selectedSong = null },
            onAddSongToFavorite = { songId ->
                favoriteViewModel.addSongToFavorite(songId)
            },
            onRemoveSongFromFavorite = { songId ->
                favoriteViewModel.removeSongToFavorite(songId)
            },
            onCreatePlaylist = {playlistName ->
                favoriteViewModel.createPlaylist(playlistName)
            },
            onAddSongToPlaylist = {playlistId, songId ->
                favoriteViewModel.addSongToPlaylist(playlistId, songId)
            },
            onSongNavigationAction = onSongNavigationAction
        )
    }
}