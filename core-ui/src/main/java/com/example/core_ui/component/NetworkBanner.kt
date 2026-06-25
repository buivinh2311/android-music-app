package com.example.core_ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_resources.ui.icon.AppIcons

@Composable
fun NetworkBanner(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = AppDimens.Space.Sm,
                end = AppDimens.Space.Sm,
                bottom = AppDimens.Space.Lg
            )
            .clip(RoundedCornerShape(AppDimens.Radius.Sm))
            .background(MaterialTheme.colorScheme.onBackground)
            .padding(AppDimens.Space.Md)
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = AppIcons.NoInternet,
            contentDescription = null,
            modifier = Modifier.size(AppDimens.Icon.Sm),
            tint = MaterialTheme.colorScheme.background
        )
        Spacer(modifier = Modifier.width(AppDimens.Space.Md))

        Text(
            text = stringResource(R.string.no_internet),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.background
        )
    }
}