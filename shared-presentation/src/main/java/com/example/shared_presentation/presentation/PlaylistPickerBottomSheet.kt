package com.example.shared_presentation.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.core_model.Playlist
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_resources.ui.icon.AppIcons
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistPickerBottomSheet(
    playlists: List<Playlist>,
    onDismiss: () -> Unit,
    onPlaylistClick: (Int) -> Unit,
    onCreateNewPlaylist: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val sheetScope = rememberCoroutineScope()
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        sheetState = sheetState
    ) {
        LazyColumn (
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(AppDimens.Space.Xs)
        ) {
            item {
                Text(
                    text = stringResource(R.string.title_add_song_to_playlist),
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(AppDimens.Space.Md))
                Surface(
                    onClick = {
                        sheetScope.launch {
                            onCreateNewPlaylist()
                            sheetState.hide()
                        }.invokeOnCompletion {
                            onDismiss()
                        }
                    },
                    shape = RoundedCornerShape(AppDimens.Radius.Sm),
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                        .padding(horizontal = AppDimens.Space.Xs)
                        .fillMaxWidth()
                        .height(AppDimens.Layout.PlaylistItemHeight)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                vertical = AppDimens.Space.Xs,
                                horizontal = AppDimens.Space.Md
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = AppIcons.Add,
                            contentDescription = null,
                            modifier = Modifier
                                .size(56.dp)
                                .padding(AppDimens.Space.Sm)
                                .clip(shape = RoundedCornerShape(AppDimens.Radius.Sm)),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.width(AppDimens.Space.Sm))

                        Text(
                            text = stringResource(R.string.action_create_new_playlist),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
            items(
                count = playlists.size,
                key = { index -> playlists[index].id }
            ) { index ->
                PlaylistItem(
                    modifier = Modifier.padding(horizontal = AppDimens.Space.Xs),
                    playlist = playlists[index],
                    onPlaylistClick = { playlistId ->
                        sheetScope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            onDismiss()
                        }
                        onPlaylistClick(playlistId)
                    }
                )
            }
        }
    }
}