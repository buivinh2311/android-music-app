package com.example.feature_player.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.feature_player.presentation.component.PlayerArtWork
import com.example.feature_player.presentation.component.PlayerControls
import com.example.feature_player.presentation.component.PlayerExtraAction
import com.example.feature_player.presentation.component.PlayerInfo
import com.example.feature_player.presentation.component.PlayerProgress
import com.example.feature_player.presentation.component.PlayerTopBar
import com.example.feature_player.presentation.viewmodel.PlayerViewModel
import com.example.shared_presentation.model.SongOptionItem
import com.example.shared_presentation.presentation.SongActionHost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    songId: String,
    onBackClick: () -> Unit,
    onSongNavigationAction: (SongOptionItem) -> Unit
) {
    var shouldShowBottomSheet by remember { mutableStateOf(false) }
    val playerViewModel: PlayerViewModel = hiltViewModel()
    val uiState by playerViewModel.uiState.collectAsState()
    LaunchedEffect(songId) {
        playerViewModel.loadSong(songId)
    }
    val playlists by playerViewModel.playlists.collectAsState()
    val song = uiState.song
    val displaySong = uiState.displaySong
    
    
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            model = song?.artworkUrl,
            placeholder = painterResource(R.drawable.ic_music_note),
            error = painterResource(R.drawable.ic_music_not_available),
            contentDescription = "Song artwork",
            modifier = Modifier
                .fillMaxSize()
                .blur(80.dp),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        )

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent
        ) { innerPadding ->
            if(uiState.isLoading) {

            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(AppDimens.Space.Lg),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    PlayerTopBar(
                        onClick = { shouldShowBottomSheet = true },
                        onBackClick = onBackClick
                    )
                    if (song != null && displaySong != null) {
                        Spacer(modifier = Modifier.height(36.dp))

                        PlayerArtWork(
                            artworkUrl = song.artworkUrl
                        ) {
                            shouldShowBottomSheet = true
                        }
                        Spacer(modifier = Modifier.height(48.dp))

                        PlayerInfo(
                            title = song.title,
                            artist = song.artist
                        )
                        Spacer(modifier = Modifier.height(40.dp))

                        PlayerProgress(
                            duration = song.duration
                        )
                        Spacer(modifier = Modifier.height(AppDimens.Space.Sm))

                        PlayerControls {
                            playerViewModel.pause()
                        }
                        Spacer(modifier = Modifier.height(AppDimens.Space.Xl))

                        PlayerExtraAction()
                        playerViewModel.play(song)
                    }
                }

                if(shouldShowBottomSheet) {
                    SongActionHost(
                        selectedSong = displaySong,
                        playlists = playlists,
                        observeFavoriteSong = { songId ->
                            playerViewModel.isFavoriteSong(songId)
                        },
                        onDismissSong = { shouldShowBottomSheet = false },
                        onAddSongToFavorite = { songId ->
                            playerViewModel.addSongToFavorite(songId)
                        },
                        onRemoveSongFromFavorite = { songId ->
                            playerViewModel.removeSongToFavorite(songId)
                        },
                        onCreatePlaylist = {playlistName ->
                            playerViewModel.createPlaylist(playlistName)
                        },
                        onAddSongToPlaylist = {playlistId, songId ->
                            playerViewModel.addSongToPlaylist(playlistId, songId)
                        },
                        onSongNavigationAction = onSongNavigationAction
                    )
                }
            }
        }
    }
}
