package com.example.feature_player.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_resources.ui.icon.AppIcons
import com.example.core_ui.component.AppButton

@Composable
fun PlayerInfo(
    modifier: Modifier = Modifier,
    title: String?,
    artist: String?
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppButton(
            painter = AppIcons.Share,
            contentDescription = stringResource(R.string.action_share),
            iconSize = AppDimens.Icon.Sm,
            rippleRadius = AppDimens.Ripple.Md,
            tint = Color.Gray
        ) { }
        Spacer(modifier = Modifier.width(AppDimens.Space.Md))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title ?: "",
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Text(
                text = artist ?: "",
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                color = Color.LightGray,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.width(AppDimens.Space.Md))

        AppButton(
            painter = AppIcons.Favorite,
            contentDescription = stringResource(R.string.action_add_to_library),
            iconSize = AppDimens.Icon.Sm,
            rippleRadius = AppDimens.Ripple.Md,
            tint = Color.Gray
        ) { }
    }
}