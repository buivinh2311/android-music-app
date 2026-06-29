package com.example.feature_player.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_resources.ui.icon.AppIcons
import com.example.core_ui.component.AppIconButton

@Composable
fun PlayerExtraAction(
    modifier: Modifier = Modifier,
    isDownloadSong: Boolean,
    onDownloadClick: () -> Unit,
    onViewComment: () -> Unit,
    onViewArtistClick: () -> Unit,
    onViewQueueClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppIconButton(
            painter = AppIcons.Comment,
            contentDescription = stringResource(R.string.action_comment),
            iconSize = AppDimens.Icon.Md,
            rippleRadius = AppDimens.Ripple.Lg,
            tint = Color.LightGray,
            rippleColor = Color.White
        ) {
            onViewComment()
        }

        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppIconButton(
                painter = AppIcons.Artist,
                contentDescription = stringResource(R.string.action_view_artist),
                iconSize = AppDimens.Icon.Md,
                rippleRadius = AppDimens.Ripple.Lg,
                tint = Color.LightGray,
                rippleColor = Color.White
            ) {
                onViewArtistClick()
            }
            Spacer(modifier = Modifier.width(AppDimens.Space.Xl))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.width(80.dp)
            ) {
                Text(
                    text = stringResource(R.string.text_capacity_128),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    modifier = modifier
                        .clickable(
                            indication = ripple(
                                bounded = false,
                                radius = AppDimens.Ripple.Lg,
                                color = Color.White
                            ),
                            interactionSource = remember {
                                MutableInteractionSource()
                            }
                        ) { }
                )
            }
            Spacer(modifier = Modifier.width(AppDimens.Space.Xl))

            AppIconButton(
                painter = AppIcons.Download,
                contentDescription = stringResource(R.string.action_download),
                iconSize = AppDimens.Icon.Md,
                rippleRadius = AppDimens.Ripple.Lg,
                tint = if(isDownloadSong) MaterialTheme.colorScheme.primary else Color.LightGray,
                rippleColor = Color.White
            ) {
                onDownloadClick()
            }
        }

        AppIconButton(
            painter = AppIcons.Queue,
            contentDescription = stringResource(R.string.action_queue_song),
            iconSize = AppDimens.Icon.Md,
            rippleRadius = AppDimens.Ripple.Lg,
            tint = Color.LightGray,
            rippleColor = Color.White
        ) {
            onViewQueueClick()
        }
    }
}