package com.example.feature_album.presentation.screen

import android.annotation.SuppressLint
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
import com.example.core_ui.component.LoadingScreen
import com.example.core_ui.menu.AppBottomBarAction
import com.example.core_ui.state.UiState
import com.example.feature_album.presentation.viewmodel.FavoriteAlbumViewModel
import com.example.shared_presentation.presentation.AlbumItem

@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun FavoriteAlbumScreen(
    onAlbumClick: (String) -> Unit,
    onBackClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    val favoriteAlbumViewModel: FavoriteAlbumViewModel = hiltViewModel()
    val uiState by favoriteAlbumViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AppBottomBar(onBottomActionClick = onBottomActionClick)
        },
        topBar = {
            AppTopBar(
                title = stringResource(R.string.action_library_album),
                onBackClick = onBackClick
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        when(val state = uiState) {
            UiState.Loading -> {
                LoadingScreen(
                    modifier = Modifier.padding(innerPadding)
                )
            }

            UiState.Empty -> {
                EmptyScreen(
                    modifier = Modifier.padding(innerPadding),
                    icon = AppIcons.Album,
                    title = stringResource(R.string.title_favorite_album_empty),
                    message = stringResource(R.string.message_favorite_album_empty)
                )
            }

            is UiState.Success -> {
                val favoriteAlbums = state.data
                LazyVerticalGrid(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(
                        top = AppDimens.Space.Lg,
                        start = AppDimens.Space.Lg,
                        end = AppDimens.Space.Lg,
                        bottom = AppDimens.Space.bottomSpace
                    ),
                    verticalArrangement = Arrangement.spacedBy(AppDimens.Space.Xl),
                    horizontalArrangement = Arrangement.spacedBy(AppDimens.Space.Md)
                ) {
                    items(
                        count = favoriteAlbums.size,
                        key = {index -> favoriteAlbums[index].id}
                    ) { index ->
                        val album = favoriteAlbums[index]
                        AlbumItem(
                            modifier = Modifier.fillMaxWidth(),
                            album = album,
                            titleMinLines = 1,
                            onAlbumClick = onAlbumClick
                        )
                    }
                }
            }
        }
    }
}