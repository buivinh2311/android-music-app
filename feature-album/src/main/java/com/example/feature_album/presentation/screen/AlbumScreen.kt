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
import com.example.feature_album.presentation.viewmodel.AlbumViewModel
import com.example.shared_presentation.presentation.AlbumItem

@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun AlbumScreen(
    selectedAction: AppBottomBarAction,
    onAlbumClick: (String) -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit,
    onBackClick: () -> Unit
) {
    val albumViewModel: AlbumViewModel = hiltViewModel()
    val uiState by albumViewModel.uiState.collectAsStateWithLifecycle()
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AppBottomBar(
                selectedAction = selectedAction,
                onBottomActionClick = onBottomActionClick
            )
        },
        topBar = {
            AppTopBar(
                title = stringResource(R.string.titlt_album_hot),
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
                    title = stringResource(R.string.title_no_album_found)
                )
            }

            is UiState.Success -> {
                val albums = state.data
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
                        count = albums.size,
                        key = { index -> albums[index].id }
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
            }
        }
    }
}