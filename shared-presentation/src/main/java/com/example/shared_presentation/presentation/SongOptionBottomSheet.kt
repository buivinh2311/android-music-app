package com.example.shared_presentation.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import com.example.core_model.Song
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_resources.ui.icon.AppIcons
import com.example.core_ui.component.AppIconButton
import com.example.shared_presentation.menu.SongOptionAction
import com.example.shared_presentation.menu.SongOptionItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongOptionBottomSheet(
    song: Song,
    isFavorite: Boolean,
    onDismiss: () -> Unit,
    onShareClick: () -> Unit,
    onSongNavigationAction: (SongOptionItem) -> Unit,
    onSongBusinessAction: (SongOptionItem) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val sheetScope = rememberCoroutineScope()
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        sheetState = sheetState
    ) {
        SongOptionBottomSheetContent(
            modifier = Modifier.padding(horizontal = AppDimens.Space.Lg),
            isFavorite = isFavorite,
            song = song,
            onShareClick = onShareClick
        ) { item ->
            when (item.action) {
                SongOptionAction.VIEW_ALBUM, SongOptionAction.VIEW_ARTIST -> {
                    onSongNavigationAction(item)
                }

                else -> {
                    onSongBusinessAction(item)
                }
            }
            sheetScope.launch {
                sheetState.hide()
            }.invokeOnCompletion {
                onDismiss()
            }
        }
    }
}

@Composable
private fun SongOptionBottomSheetContent(
    modifier: Modifier = Modifier,
    song: Song,
    isFavorite: Boolean,
    onShareClick: () -> Unit,
    onClick: (SongOptionItem) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = song.artworkUrl,
                contentDescription = null,
                placeholder = painterResource(R.drawable.logo),
                error = painterResource(R.drawable.logo),
                modifier = Modifier
                    .size(AppDimens.ImageSize.Md)
                    .clip(shape = RoundedCornerShape(AppDimens.Radius.Sm)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(AppDimens.Space.Sm))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = song.title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(AppDimens.Space.Xs))
                Text(
                    text = song.artist,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.width(AppDimens.Space.Xs))

            AppIconButton(
                painter = AppIcons.Share,
                contentDescription = stringResource(R.string.action_view_more),
                iconSize = AppDimens.Icon.Sm,
                rippleRadius = AppDimens.Ripple.Sm,
                tint = MaterialTheme.colorScheme.onBackground,
                rippleColor = MaterialTheme.colorScheme.onBackground
            ) {
                onShareClick()
            }
        }
        HorizontalDivider(modifier = Modifier.padding(vertical = AppDimens.Space.Md))

        songOptions(song, isFavorite).forEach { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onClick(item)
                    }
                    .padding(vertical = AppDimens.Space.Md),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = item.icon,
                    contentDescription = null,
                    modifier = Modifier.size(AppDimens.Icon.Sm),
                    tint = item.iconColor
                )
                Spacer(modifier = Modifier.width(AppDimens.Space.Md))
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun songOptions(
    song: Song,
    isFavorite: Boolean
): List<SongOptionItem> {
    val options = mutableListOf<SongOptionItem>().apply {

        add(
            SongOptionItem(
                id = song.id,
                icon = AppIcons.Download,
                iconColor = MaterialTheme.colorScheme.onBackground,
                title = stringResource(R.string.action_download),
                action = SongOptionAction.DOWNLOAD
            )
        )

        add(
            SongOptionItem(
                id = song.id,
                icon = if(isFavorite) AppIcons.Favorite_filled else AppIcons.Favorite,
                iconColor = if(isFavorite) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onBackground,
                title = if(isFavorite) stringResource(R.string.added_song_to_library)
                else stringResource(R.string.action_add_to_library),
                action = SongOptionAction.ADD_TO_LIBRARY
            )
        )

        add(
            SongOptionItem(
                id = song.id,
                icon = AppIcons.AddToPlaylist,
                iconColor = MaterialTheme.colorScheme.onBackground,
                title = stringResource(R.string.action_add_to_playlist),
                action = SongOptionAction.ADD_TO_PLAYLIST
            )
        )

        if (song.album != null) {
            add(
                SongOptionItem(
                    id = song.id,
                    album = song.album,
                    icon = AppIcons.Album,
                    iconColor = MaterialTheme.colorScheme.onBackground,
                    title = stringResource(R.string.action_view_album),
                    action = SongOptionAction.VIEW_ALBUM
                )
            )
        }

        add(
            SongOptionItem(
                id = song.id,
                artist = song.artist,
                icon = AppIcons.Artist,
                iconColor = MaterialTheme.colorScheme.onBackground,
                title = stringResource(R.string.action_view_artist),
                action = SongOptionAction.VIEW_ARTIST
            )
        )

        add(
            SongOptionItem(
                id = song.id,
                icon = AppIcons.SimilarContent,
                iconColor = MaterialTheme.colorScheme.onBackground,
                title = stringResource(R.string.action_play_similar_content),
                action = SongOptionAction.SIMILAR_CONTENT
            )
        )

        add(
            SongOptionItem(
                id = song.id,
                icon = AppIcons.Ringtone,
                iconColor = MaterialTheme.colorScheme.onBackground,
                title = stringResource(R.string.action_set_ringtone),
                action = SongOptionAction.RING_TONE
            )
        )

        add(
            SongOptionItem(
                id = song.id,
                icon = AppIcons.Equalizer,
                iconColor = MaterialTheme.colorScheme.onBackground,
                title = stringResource(R.string.action_equalizer),
                action = SongOptionAction.EQUALIZER
            )
        )

        add(
            SongOptionItem(
                id = song.id,
                icon = AppIcons.Comment,
                title = stringResource(R.string.action_comment),
                iconColor = MaterialTheme.colorScheme.onBackground,
                action = SongOptionAction.COMMENT
            )
        )

        add(
            SongOptionItem(
                id = song.id,
                icon = AppIcons.Block,
                iconColor = MaterialTheme.colorScheme.onBackground,
                title = stringResource(R.string.action_block),
                action = SongOptionAction.BLOCK
            )
        )

        add(
            SongOptionItem(
                id = song.id,
                icon = AppIcons.ReportError,
                iconColor = MaterialTheme.colorScheme.onBackground,
                title = stringResource(R.string.action_report_error),
                action = SongOptionAction.REPORT
            )
        )
    }
    return options
}