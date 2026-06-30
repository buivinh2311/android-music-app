package com.example.feature_playlist.presentation.component

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
import com.example.core_model.Playlist
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_resources.ui.icon.AppIcons
import com.example.core_ui.component.AppIconButton
import com.example.feature_playlist.menu.PlaylistOptionAction
import com.example.feature_playlist.menu.PlaylistOptionItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistOptionBottomSheet (
    playlist: Playlist,
    onDismiss: () -> Unit,
    onShareClick: () -> Unit,
    onPlaylistOptionClick: (PlaylistOptionItem) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val sheetScope = rememberCoroutineScope()
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        sheetState = sheetState
    ) {
        PlaylistOptionBottomSheetContent(
            playlist = playlist,
            onShareClick = onShareClick
        ) { item ->
            onPlaylistOptionClick(item)
            sheetScope.launch {
                sheetState.hide()
            }.invokeOnCompletion {
                onDismiss()
            }
        }
    }
}

@Composable
private fun PlaylistOptionBottomSheetContent(
    modifier: Modifier = Modifier,
    playlist: Playlist,
    onShareClick: () -> Unit,
    onClick: (PlaylistOptionItem) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = AppDimens.Space.Lg),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = playlist.artwork,
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
                    text = playlist.name,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(AppDimens.Space.Xs))
                Text(
                    text = playlist.size.toString() + stringResource(R.string.text_song),
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

        playlistOptions(playlist).forEach { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onClick(item)
                    }
                    .padding(
                        vertical = AppDimens.Space.Md,
                        horizontal = AppDimens.Space.Lg
                    ),
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
private fun playlistOptions(
    playlist: Playlist
): List<PlaylistOptionItem> {
    val options = mutableListOf<PlaylistOptionItem>().apply {

        add(
            PlaylistOptionItem(
                id = playlist.id,
                icon = AppIcons.Edit,
                iconColor = MaterialTheme.colorScheme.onBackground,
                title = stringResource(R.string.action_rename_playlist),
                action = PlaylistOptionAction.EDIT
            )
        )

        add(
            PlaylistOptionItem(
                id = playlist.id,
                icon = AppIcons.Delete,
                iconColor = MaterialTheme.colorScheme.onBackground,
                title = stringResource(R.string.action_delete_playlist),
                action = PlaylistOptionAction.DELETE
            )
        )
    }
    return options
}