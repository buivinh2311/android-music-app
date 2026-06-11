package com.example.feature_library.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_ui.component.AppBottomBar
import com.example.core_ui.component.AppTopBar
import com.example.core_ui.component.ViewAllButton
import com.example.core_ui.menu.AppBottomBarAction
import com.example.shared_presentation.model.SongOptionItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    onRecentClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onMorePlaylistClick: () -> Unit,
    onPlaylistClick: (Int) -> Unit,
    onSearchClick: () -> Unit,
    onSongClick: (String) -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit,
    onSongNavigationAction: (SongOptionItem) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AppBottomBar(onBottomActionClick = onBottomActionClick)
        },
        topBar = {
            AppTopBar(title = stringResource(R.string.title_library))
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(
                vertical = AppDimens.Space.Lg
            )
        ) {
            item {
                LibraryCategory(onFavoriteClick = onFavoriteClick)
                Spacer(modifier = Modifier.height(AppDimens.Space.Xl))
                ViewAllButton(
                    title = stringResource(R.string.title_library_heard_recently),
                    onMoreClick = onRecentClick
                )
//                SongLazyHorizontalGrid(
//                    rowWidth = 300.dp,
//                    onSongClick = onSongClick
//                )
            }

            item {
                Spacer(modifier = Modifier.height(AppDimens.Space.Xl))
                LibraryPlaylistHeader(
                    modifier = Modifier.padding(horizontal = AppDimens.Space.Xs),
                    onMorePlaylistClick = onMorePlaylistClick
                )
                Spacer(modifier = Modifier.height(AppDimens.Space.Sm))
            }

//            items(20) {
//                PlaylistItem(
//                    modifier = Modifier.padding(horizontal = AppDimens.Space.Xs),
//                    onPlaylistClick = onPlaylistClick
//                )
//            }
        }
    }
}