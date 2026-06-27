package com.example.feature_player.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core_model.playback.RepeatMode
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_resources.ui.icon.AppIcons
import com.example.core_ui.component.AppIconButton

@Composable
fun PlayerControls(
    modifier: Modifier = Modifier,
    isPlaying: Boolean,
    onPlayClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    isShuffleEnabled: Boolean,
    onShuffleClick: () -> Unit,
    repeatMode: RepeatMode,
    onRepeatModeClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppIconButton(
            painter = AppIcons.Mix,
            contentDescription = stringResource(R.string.action_mix),
            iconSize = AppDimens.Icon.Md,
            rippleRadius = AppDimens.Ripple.Lg,
            tint = if(isShuffleEnabled) MaterialTheme.colorScheme.primary else Color.LightGray,
            rippleColor = Color.White
        ) {
            onShuffleClick()
        }

        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppIconButton(
                painter = AppIcons.SkipPrevious,
                contentDescription = stringResource(R.string.action_skip_previous),
                iconSize = AppDimens.Icon.Lg,
                rippleRadius = AppDimens.Ripple.Lg,
                tint = Color.White,
                rippleColor = Color.White
            ) {
                onPreviousClick()
            }
            Spacer(modifier = Modifier.width(AppDimens.Space.Xl))

            AppIconButton(
                painter = if(isPlaying) AppIcons.Play else AppIcons.Pause,
                contentDescription = stringResource(R.string.action_play),
                iconSize = 72.dp,
                rippleRadius = AppDimens.Ripple.Lg,
                tint = Color.White,
                rippleColor = Color.White
            ) {
                onPlayClick()
            }
            Spacer(modifier = Modifier.width(AppDimens.Space.Xl))

            AppIconButton(
                painter = AppIcons.SkipNext,
                contentDescription = stringResource(R.string.action_skip_next),
                iconSize = AppDimens.Icon.Lg,
                rippleRadius = AppDimens.Ripple.Lg,
                tint = Color.White,
                rippleColor = Color.White
            ) {
                onNextClick()
            }
        }

        AppIconButton(
            painter = if(repeatMode == RepeatMode.ONE) AppIcons.RepeatOne else AppIcons.Repeat,
            contentDescription = stringResource(R.string.action_change_repeat),
            iconSize = AppDimens.Icon.Md,
            rippleRadius = AppDimens.Ripple.Lg,
            tint = if(repeatMode == RepeatMode.OFF) Color.LightGray
            else MaterialTheme.colorScheme.primary,
            rippleColor = Color.White
        ) {
            onRepeatModeClick()
        }
    }
}