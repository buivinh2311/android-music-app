package com.example.feature_album.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_resources.ui.icon.AppIcons
import com.example.core_ui.ui.AppButton

@Composable
fun AlbumAction(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        AppButton(
            painter = AppIcons.Download,
            contentDescription = stringResource(R.string.action_download),
            iconSize = AppDimens.Icon.Md,
            rippleRadius = AppDimens.Ripple.Sm,
            tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        ) { }

        Button(
            onClick = {},
            shape = RoundedCornerShape(48.dp),
            modifier = Modifier
                .width(200.dp)
                .padding(horizontal = AppDimens.Space.Xl)
        ) {
            Text(
                text = stringResource(R.string.action_play_music),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
        }

        AppButton(
            painter = AppIcons.Favorite,
            contentDescription = stringResource(R.string.action_add_to_library),
            iconSize = AppDimens.Icon.Md,
            rippleRadius = AppDimens.Ripple.Sm,
            tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        ) { }
    }
}