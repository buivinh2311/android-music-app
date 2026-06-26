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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_model.QueueSource
import com.example.core_model.Song
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_resources.ui.icon.AppIcons
import com.example.core_ui.component.AppBottomBar
import com.example.core_ui.component.AppTopBar
import com.example.core_ui.component.EmptySection
import com.example.core_ui.component.LoadingSection
import com.example.core_ui.component.showToast
import com.example.core_ui.menu.AppBottomBarAction
import com.example.core_ui.state.UiState
import com.example.feature_artist.presentation.component.ArtistAction
import com.example.feature_artist.presentation.component.ArtistInformation
import com.example.feature_artist.presentation.viewmodel.ArtistDetailViewModel
import com.example.shared_presentation.menu.SongOptionItem
import com.example.shared_presentation.presentation.SongActionHost
import com.example.shared_presentation.presentation.SongItem

@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun ArtistDetailScreen(
    artistName: String,
    isConnect: Boolean,
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
        artistDetailViewModel.loadSongs(artistName)
    }
    val playlists by artistDetailViewModel.playlists
        .collectAsStateWithLifecycle(emptyList())

    val isFavoriteArtist by artistDetailViewModel.isFavoriteArtist(artistName)
        .collectAsStateWithLifecycle(false)
    val context = LocalContext.current
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AppBottomBar(onBottomActionClick = onBottomActionClick)
        },
        topBar = {
            AppTopBar(
                title = artistName,
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
            contentPadding = PaddingValues(
                top = AppDimens.Space.Lg,
                bottom = AppDimens.Space.bottomSpace
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                when(val state = uiState.artist) {
                    UiState.Loading -> {
                        LoadingSection()
                    }

                    UiState.Empty -> {
                        EmptySection(
                            icon = AppIcons.Artist,
                            title = stringResource(R.string.title_no_artist_found)
                        )
                    }

                    is UiState.Success -> {
                        val artist = state.data
                        ArtistInformation(
                            artist = artist,
                            isFavoriteArtist = isFavoriteArtist
                        )
                        Spacer(modifier = Modifier.height(AppDimens.Space.Md))

                        ArtistAction(
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
                    }
                }
                Spacer(modifier = Modifier.height(AppDimens.Space.Xl))
            }

            when(val state = uiState.songs) {
                UiState.Loading -> {
                    item {
                        LoadingSection()
                    }
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
                    val songs = state.data
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
                                if(isConnect) {
                                    artistDetailViewModel.play(
                                        queueSource = QueueSource.ARTIST,
                                        queue = songs,
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
                            onMoreClick = { song ->
                                selectedSong = song
                            }
                        )
                    }
                }
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
                artistDetailViewModel.removeSongFromFavorite(songId)
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