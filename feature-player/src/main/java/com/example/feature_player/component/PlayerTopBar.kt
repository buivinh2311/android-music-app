package com.example.feature_player.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.example.core_model.Song
import com.example.core_model.playback.QueueSource
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_resources.ui.icon.AppIcons
import com.example.core_ui.component.AppIconButton

@Composable
fun PlayerTopBar(
    modifier: Modifier = Modifier,
    song: Song,
    queueSource: QueueSource,
    sourceName: String,
    onClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppIconButton(
            painter = AppIcons.Down,
            contentDescription = stringResource(R.string.action_down),
            iconSize = AppDimens.Icon.Md,
            rippleRadius = AppDimens.Ripple.Md,
            tint = Color.White,
            rippleColor = Color.White
        ) {
            onBackClick()
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = stringResource(R.string.play_from),
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = when(queueSource) {
                    QueueSource.FAVORITE -> stringResource(R.string.label_favorite)
                    QueueSource.RECOMMENDED -> stringResource(R.string.title_home_recommended_song)
                    QueueSource.RECENT -> stringResource(R.string.title_library_heard_recently)
                    QueueSource.MOST_HEARD -> stringResource(R.string.title_discovery_most_listened)
                    QueueSource.FOR_YOU -> stringResource(R.string.title_discovery_for_your)
                    QueueSource.ARTIST -> song.artist
                    QueueSource.ALBUM -> song.album.orEmpty()
                    QueueSource.PLAYLIST -> sourceName
                    QueueSource.DOWNLOAD -> stringResource(R.string.action_download)
                    else -> "_"
                },
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        AppIconButton(
            painter = AppIcons.More,
            contentDescription = stringResource(R.string.action_view_more),
            iconSize = AppDimens.Icon.Md,
            rippleRadius = AppDimens.Ripple.Md,
            tint = Color.White,
            rippleColor = Color.White
        ) { onClick() }
    }
}