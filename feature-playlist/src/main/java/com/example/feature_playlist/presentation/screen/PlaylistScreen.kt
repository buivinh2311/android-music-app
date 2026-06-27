package com.example.feature_playlist.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_resources.ui.icon.AppIcons
import com.example.core_ui.component.AppBottomBar
import com.example.core_ui.component.AppTopBar
import com.example.core_ui.component.EmptyScreen
import com.example.core_ui.menu.AppBottomBarAction
import com.example.feature_playlist.presentation.viewmodel.PlaylistViewModel
import com.example.shared_presentation.presentation.PlaylistItem

@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun PlaylistScreen(
    onPlaylistClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    val playlistViewModel: PlaylistViewModel = hiltViewModel()
    val playlists by playlistViewModel.playlist.collectAsStateWithLifecycle(emptyList())
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AppBottomBar(onBottomActionClick = onBottomActionClick)
        },
        topBar = {
            AppTopBar(
                title = stringResource(R.string.title_library_playlist),
                onBackClick = onBackClick
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        if(playlists.isEmpty()) {
            EmptyScreen(
                modifier = Modifier.padding(innerPadding),
                icon = AppIcons.AddToPlaylist,
                title = stringResource(R.string.title_playlist_empty),
                message = stringResource(R.string.message_playlist_empty)
            )
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
                items(
                    count = playlists.size,
                    key = { index -> playlists[index].id }
                ) { index ->
                    PlaylistItem(
                        modifier = Modifier.padding(horizontal = AppDimens.Space.Xs),
                        playlist = playlists[index],
                        onPlaylistClick = onPlaylistClick
                    )
                }
            }
        }
    }
}