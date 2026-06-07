package com.example.core_ui.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.core_model.DisplaySong
import com.example.core_resources.ui.dimen.AppDimens

@Composable
fun SongLazyHorizontalGrid(
    modifier: Modifier = Modifier,
    songs: List<DisplaySong>,
    rowWidth: Dp,
    onSongClick: (String) -> Unit,
    onMoreClick: (DisplaySong) -> Unit
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(3),
        modifier = modifier.height(216.dp),
        verticalArrangement = Arrangement.spacedBy(AppDimens.Space.Xs),
        horizontalArrangement = Arrangement.spacedBy(AppDimens.Space.Sm)
    ) {
        items(
            count = songs.size,
            key = { index -> songs[index].id }
        ) { index ->
            SongItem(
                modifier = Modifier
                    .padding(horizontal = AppDimens.Space.Xs)
                    .width(rowWidth),
                song = songs[index],
                onSongClick = onSongClick,
                onMoreClick = onMoreClick
            )
        }
    }
}