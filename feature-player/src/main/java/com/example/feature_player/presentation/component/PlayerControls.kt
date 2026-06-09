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
import androidx.compose.ui.unit.dp
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_resources.ui.icon.AppIcons
import com.example.core_ui.component.AppButton

@Composable
fun PlayerControls(
    modifier: Modifier = Modifier,
    onPlayClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppButton(
            painter = AppIcons.Mix,
            contentDescription = stringResource(R.string.action_mix),
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
                painter = AppIcons.SkipPrevious,
                contentDescription = stringResource(R.string.action_skip_previous),
                iconSize = AppDimens.Icon.Lg,
                rippleRadius = AppDimens.Ripple.Lg,
                tint = Color.White
            ) { }
            Spacer(modifier = Modifier.width(AppDimens.Space.Xl))

            AppButton(
                painter = AppIcons.Play,
                contentDescription = stringResource(R.string.action_play),
                iconSize = 72.dp,
                rippleRadius = AppDimens.Ripple.Lg,
                tint = Color.White
            ) {
                onPlayClick()
            }
            Spacer(modifier = Modifier.width(24.dp))

            AppButton(
                painter = AppIcons.SkipNext,
                contentDescription = stringResource(R.string.action_skip_next),
                iconSize = AppDimens.Icon.Lg,
                rippleRadius = AppDimens.Ripple.Lg,
                tint = Color.White
            ) { }
        }

        AppButton(
            painter = AppIcons.Repeat,
            contentDescription = stringResource(R.string.action_repeat),
            iconSize = AppDimens.Icon.Sm,
            rippleRadius = AppDimens.Ripple.Lg,
            tint = Color.Gray
        ) { }
    }
}