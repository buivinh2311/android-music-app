package com.example.core_ui.ui

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
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_resources.ui.icon.AppIcons

@Composable
fun PlaylistItem(
    modifier: Modifier = Modifier,
    onPlaylistClick: (Int) -> Unit
) {
    Surface(
        onClick = { onPlaylistClick(1) },
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
                model = "ddd",
                contentDescription = null,
                modifier = Modifier
                    .size(AppDimens.ImageSize.Md)
                    .clip(shape = RoundedCornerShape(AppDimens.Space.Sm)),
                placeholder = painterResource(R.drawable.ic_music_note),
                error = painterResource(R.drawable.ic_music_not_available),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(AppDimens.Space.Sm))

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = "Tên playlist",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = "so bai hat",
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.width(AppDimens.Space.Xs))

            AppButton(
                painter = AppIcons.Favorite,
                contentDescription = stringResource(R.string.action_library_favorite),
                iconSize = AppDimens.Icon.Sm,
                rippleRadius = AppDimens.Ripple.Md,
                tint = Color.Gray
            ) { }
        }
    }
}