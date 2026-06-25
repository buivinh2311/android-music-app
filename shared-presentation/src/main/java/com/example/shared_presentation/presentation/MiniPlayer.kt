package com.example.shared_presentation.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.core_model.Song
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_resources.ui.icon.AppIcons
import com.example.core_ui.component.AppIconButton

@Composable
fun MiniPlayer(
    modifier: Modifier = Modifier,
    song: Song,
    isFavoriteSong: Boolean,
    isPlaying: Boolean,
    onMiniPlayerClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onTogglePlayClick: () -> Unit,
    onNextClick: () -> Unit
) {
    val shape = RoundedCornerShape(AppDimens.Radius.Sm)
    Surface(
        onClick = {
            onMiniPlayerClick()
        },
        tonalElevation = AppDimens.Space.Xs,
        shape = RoundedCornerShape(AppDimens.Radius.Sm),
        color = Color.Transparent,
        modifier = modifier
            .fillMaxWidth()
            .height(AppDimens.Layout.MiniPlayerHeight)
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = AppDimens.Space.Sm,
                    end = AppDimens.Space.Sm,
                    bottom = AppDimens.Space.Sm
                )
                .shadow(
                    elevation = 2.dp,
                    shape = shape,
                    clip = false
                )
                .clip(RoundedCornerShape(AppDimens.Radius.Sm))
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .border(
                    width = AppDimens.Border.Thin,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                    shape = shape
                )
            ,
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

            AppIconButton(
                painter = if (isFavoriteSong) AppIcons.Favorite_filled else AppIcons.Favorite,
                contentDescription = stringResource(R.string.action_add_to_library),
                iconSize = AppDimens.Icon.Md,
                rippleRadius = AppDimens.Ripple.Sm,
                tint = if (isFavoriteSong) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onBackground,
                rippleColor = MaterialTheme.colorScheme.onBackground,
                onClick = { onFavoriteClick() }
            )
            Spacer(modifier = Modifier.width(AppDimens.Space.Sm))

            AppIconButton(
                painter = if (isPlaying) AppIcons.PauseMini else AppIcons.PlayMini,
                contentDescription = stringResource(R.string.action_play),
                iconSize = AppDimens.Icon.Lg,
                rippleRadius = AppDimens.Ripple.Sm,
                tint = MaterialTheme.colorScheme.onBackground,
                rippleColor = MaterialTheme.colorScheme.onBackground,
                onClick = { onTogglePlayClick() }
            )
            Spacer(modifier = Modifier.width(AppDimens.Space.Xs))

            AppIconButton(
                painter = AppIcons.SkipNext,
                contentDescription = stringResource(R.string.action_skip_next),
                iconSize = AppDimens.Icon.Lg,
                rippleRadius = AppDimens.Ripple.Sm,
                tint = MaterialTheme.colorScheme.onBackground,
                rippleColor = MaterialTheme.colorScheme.onBackground,
                onClick = { onNextClick() }
            )
            Spacer(modifier = Modifier.width(AppDimens.Space.Sm))
        }
    }
}