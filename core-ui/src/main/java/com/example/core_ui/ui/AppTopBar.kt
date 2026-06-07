package com.example.core_ui.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_resources.ui.icon.AppIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    title: String,
    onBackClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(
                start = AppDimens.Space.Lg,
                end = AppDimens.Space.Lg,
                top = AppDimens.Space.Sm,
                bottom = AppDimens.Space.Xs
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(onBackClick != null) {
            AppButton(
                painter = AppIcons.Back,
                contentDescription = stringResource(R.string.action_navigate_up),
                iconSize = AppDimens.Icon.Sm,
                rippleRadius = AppDimens.Ripple.Sm,
                tint = MaterialTheme.colorScheme.onBackground
            ) {
                onBackClick()
            }
            Spacer(modifier = Modifier.width(AppDimens.Space.Md))
        }

        Text(
            modifier = Modifier.weight(1f),
            text = title,
            style = if(onBackClick == null) MaterialTheme.typography.headlineMedium
                    else MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        if(onBackClick == null) {
            AppButton(
                painter = AppIcons.Mic,
                contentDescription = stringResource(R.string.action_search_by_voice),
                iconSize = AppDimens.Icon.Md,
                rippleRadius = AppDimens.Ripple.Sm,
                tint = MaterialTheme.colorScheme.onBackground
            ) { }
            Spacer(modifier = Modifier.width(AppDimens.Space.Lg))

            AppButton(
                painter = AppIcons.Search,
                contentDescription = stringResource(R.string.action_search),
                iconSize = AppDimens.Icon.Md,
                rippleRadius = AppDimens.Ripple.Sm,
                tint = MaterialTheme.colorScheme.onBackground
            ) { }
        }
    }
}