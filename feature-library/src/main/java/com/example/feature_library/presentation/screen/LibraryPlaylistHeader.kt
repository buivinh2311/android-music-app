package com.example.feature_library.presentation.screen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_resources.ui.icon.AppIcons
import com.example.core_ui.component.ViewAllButton

@Composable
fun LibraryPlaylistHeader(
    modifier: Modifier = Modifier,
    onMorePlaylistClick: () -> Unit
) {
    ViewAllButton(
        title = stringResource(R.string.title_library_playlist),
        onMoreClick = onMorePlaylistClick
    )
    Surface(
        onClick = {},
        shape = RoundedCornerShape(AppDimens.Radius.Sm),
        color = MaterialTheme.colorScheme.surface,
        modifier = modifier
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
                tint = Color.Gray
            )
            Spacer(modifier = Modifier.width(AppDimens.Space.Sm))

            Text(
                text = "tao playist moi",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}