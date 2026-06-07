package com.example.feature_album.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_ui.data.AppBottomBarAction
import com.example.core_ui.ui.AlbumItem
import com.example.core_ui.ui.AppBottomBar
import com.example.core_ui.ui.AppTopBar
import com.example.feature_album.presentation.viewmodel.AlbumViewModel

@Composable
fun AlbumScreen(
    onAlbumClick: (String) -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit,
    onBackClick: () -> Unit
) {
    val albumViewModel: AlbumViewModel = hiltViewModel()
    val uiState by albumViewModel.uiState.collectAsState()
    val albums = uiState.albums
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AppBottomBar(onBottomActionClick = onBottomActionClick)
        },
        topBar = {
            AppTopBar(
                title = stringResource(R.string.titlt_album_hot),
                onBackClick = onBackClick
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        if(albums.isNotEmpty()) {
            LazyVerticalGrid(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(AppDimens.Space.Lg),
                verticalArrangement = Arrangement.spacedBy(AppDimens.Space.Xl),
                horizontalArrangement = Arrangement.spacedBy(AppDimens.Space.Md)
            ) {
                items(
                    count = albums.size,
                    key = {index -> albums[index].id}
                ) { index ->
                    val album = albums[index]
                    AlbumItem(
                        modifier = Modifier.fillMaxWidth(),
                        album = album,
                        titleMinLines = 1,
                        onAlbumClick = onAlbumClick
                    )
                }
            }
        } else if(uiState.isLoading) {

        }
    }
}