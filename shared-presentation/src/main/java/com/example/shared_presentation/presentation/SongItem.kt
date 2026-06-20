package com.example.shared_presentation.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import com.example.core_model.Song
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_resources.ui.icon.AppIcons
import com.example.core_ui.component.AppIconButton

@Composable
fun SongItem(
    modifier: Modifier = Modifier,
    song: Song,
    onSongClick: (Song) -> Unit,
    onMoreClick: (Song) -> Unit
) {
    Surface(
        onClick = {
            onSongClick(song)
        },
        shape = RoundedCornerShape(AppDimens.Radius.Sm),
        color = Color.Transparent,
        modifier = modifier.height(AppDimens.Layout.SongItemHeight)
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = AppDimens.Space.Md,
                    top = AppDimens.Space.Sm,
                    end = AppDimens.Space.Sm,
                    bottom = AppDimens.Space.Sm
                ),
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
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.width(AppDimens.Space.Xs))

            AppIconButton(
                painter = AppIcons.More,
                contentDescription = stringResource(R.string.action_view_more),
                iconSize = AppDimens.Icon.Sm,
                rippleRadius = AppDimens.Ripple.Sm,
                tint = MaterialTheme.colorScheme.onBackground,
                rippleColor = MaterialTheme.colorScheme.onBackground
            ) {
                onMoreClick(song)
            }
        }
    }
}