package com.example.feature_player.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_resources.ui.icon.AppIcons
import com.example.core_ui.ui.AppButton

@Composable
fun PlayerExtraAction(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppButton(
            painter = AppIcons.Comment,
            contentDescription = stringResource(R.string.action_comment),
            iconSize = AppDimens.Icon.Sm,
            rippleRadius = AppDimens.Ripple.Lg,
            tint = Color.Gray
        ) { }

        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppButton(
                painter = AppIcons.AddToLibrary,
                contentDescription = stringResource(R.string.action_add_to_library),
                iconSize = AppDimens.Icon.Sm,
                rippleRadius = AppDimens.Ripple.Lg,
                tint = Color.Gray
            ) { }
            Spacer(modifier = Modifier.width(AppDimens.Space.Xl))

            AppButton(
                painter = AppIcons.Play,
                contentDescription = stringResource(R.string.action_play),
                iconSize = AppDimens.Icon.Sm,
                rippleRadius = AppDimens.Ripple.Lg,
                tint = Color.Gray
            ) { }
            Spacer(modifier = Modifier.width(AppDimens.Space.Xl))

            AppButton(
                painter = AppIcons.Download,
                contentDescription = stringResource(R.string.action_download),
                iconSize = AppDimens.Icon.Sm,
                rippleRadius = AppDimens.Ripple.Lg,
                tint = Color.Gray
            ) { }
        }

        AppButton(
            painter = AppIcons.Artist,
            contentDescription = stringResource(R.string.action_view_artist),
            iconSize = AppDimens.Icon.Sm,
            rippleRadius = AppDimens.Ripple.Lg,
            tint = Color.Gray
        ) { }
    }
}