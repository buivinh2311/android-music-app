package com.example.feature_artist.presentation.screen

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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_ui.menu.AppBottomBarAction
import com.example.core_ui.component.AppBottomBar
import com.example.core_ui.component.AppTopBar
import com.example.core_ui.component.ArtistItem
import com.example.feature_artist.presentation.viewmodel.ArtistViewModel

@Composable
fun ArtistScreen(
    onArtistClick: (String) -> Unit,
    onBackCLick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    val artistViewModel: ArtistViewModel = hiltViewModel()
    val uiState by artistViewModel.uiState.collectAsStateWithLifecycle()
    val artists = uiState.artists
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AppBottomBar(onBottomActionClick = onBottomActionClick)
        },
        topBar = {
            AppTopBar(
                title = stringResource(R.string.title_artist_hot),
                onBackClick = onBackCLick
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        if(uiState.isLoading) {

        } else {
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
                    count = artists.size,
                    key = { index -> artists[index].id }
                ) { index ->
                    ArtistItem(
                        modifier = Modifier.fillMaxWidth(),
                        artist = artists[index],
                        titleMinLines = 1,
                        onArtistClick = onArtistClick
                    )
                }
            }
        }
    }
}