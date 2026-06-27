package com.example.shared_presentation.presentation

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
import com.example.core_model.Song
import com.example.core_resources.ui.dimen.AppDimens

@Composable
fun SongLazyHorizontalGrid(
    modifier: Modifier = Modifier,
    songs: List<Song>,
    rowWidth: Dp,
    onSongClick: (Song) -> Unit,
    onSongOptionClick: (Song) -> Unit
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(3),
        modifier = modifier.height(216.dp),
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
                onSongOptionClick = onSongOptionClick
            )
        }
    }
}