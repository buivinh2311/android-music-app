package com.example.feature_artist.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_model.Artist
import com.example.core_model.Song
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_ui.component.AppBottomBar
import com.example.core_ui.component.AppTopBar
import com.example.core_ui.component.SongItem
import com.example.core_ui.component.showToast
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_artist.presentation.component.ArtistInformation
import com.example.feature_artist.presentation.viewmodel.ArtistDetailViewModel
import com.example.shared_presentation.model.SongOptionItem
import com.example.shared_presentation.presentation.SongActionHost

@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun ArtistDetailScreen(
    artistName: String,
    onSongClick: (String) -> Unit,
    onBackCLick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit,
    onSongNavigationAction: (SongOptionItem) -> Unit
) {
    var selectedSong: Song? by remember {
        mutableStateOf(null)
    }
    val artistDetailViewModel: ArtistDetailViewModel = hiltViewModel()
    val uiState by artistDetailViewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(artistName) {
        artistDetailViewModel.loadArtistDetail(artistName)
    }
    val artist = uiState.artist ?: Artist(0, artistName, "", 0)
    val songs = uiState.songs
    val playlists by artistDetailViewModel.playlists
        .collectAsStateWithLifecycle(emptyList())
    val isFavoriteArtist by artistDetailViewModel
        .isFavoriteArtist(artistName)
        .collectAsStateWithLifecycle(false)
    val context = LocalContext.current
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AppBottomBar(onBottomActionClick = onBottomActionClick)
        },
        topBar = {
            AppTopBar(
                title = artist.name,
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
                    ArtistInformation(
                        artist = artist,
                        isFavoriteArtist = isFavoriteArtist,
                        onFollowClick = { artistName ->
                            if(isFavoriteArtist) {
                                artistDetailViewModel.removeArtistFromFavorite(artistName)
                                showToast(
                                    context,
                                    message = context.getString(
                                        R.string.remove_artist_from_favorite_success,
                                        artistName
                                    )
                                )
                            } else {
                                artistDetailViewModel.addArtistToFavorite(artistName)
                                showToast(
                                    context,
                                    message = context.getString(
                                        R.string.add_artist_to_favorite_success,
                                        artistName
                                    )
                                )
                            }
                        }
                    )
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
                            artistDetailViewModel.play(
                                queueSource = artistName,
                                queue = songs,
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
                    artistDetailViewModel.isFavoriteSong(songId)
                },
                onDismissSong = { selectedSong = null },
                onAddSongToFavorite = { songId ->
                    artistDetailViewModel.addSongToFavorite(songId)
                },
                onRemoveSongFromFavorite = { songId ->
                    artistDetailViewModel.removeSongToFavorite(songId)
                },
                onCreatePlaylist = {playlistName ->
                    artistDetailViewModel.createPlaylist(playlistName)
                },
                onAddSongToPlaylist = {playlistId, songId ->
                    artistDetailViewModel.addSongToPlaylist(playlistId, songId)
                },
                onSongNavigationAction = onSongNavigationAction
            )
        }
    }
}