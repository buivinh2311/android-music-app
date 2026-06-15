package com.example.core_ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import com.example.core_model.Playlist
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_resources.ui.icon.AppIcons

@Composable
fun PlaylistItem(
    modifier: Modifier = Modifier,
    playlist: Playlist,
    onPlaylistClick: (Int) -> Unit
) {
    Surface(
        onClick = { onPlaylistClick(playlist.id) },
        shape = RoundedCornerShape(AppDimens.Radius.Sm),
        color = Color.Transparent,
        modifier = modifier.height(AppDimens.Layout.PlaylistItemHeight)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = AppDimens.Space.Md,
                    end = AppDimens.Space.Lg,
                    top = AppDimens.Space.Xs,
                    bottom = AppDimens.Space.Xs
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = playlist.artwork,
                contentDescription = null,
                modifier = Modifier
                    .size(AppDimens.ImageSize.Md)
                    .clip(shape = RoundedCornerShape(AppDimens.Space.Sm)),
                placeholder = painterResource(R.drawable.logo),
                error = painterResource(R.drawable.logo),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(AppDimens.Space.Sm))

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = playlist.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(AppDimens.Space.Xs))
                Text(
                    text = playlist.size.toString() + stringResource(R.string.text_song),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}