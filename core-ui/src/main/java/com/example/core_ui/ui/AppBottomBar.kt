package com.example.core_ui.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_resources.ui.icon.AppIcons
import com.example.core_ui.data.AppBottomBarAction
import com.example.core_ui.data.AppBottomBarItem

@Composable
fun AppBottomBar(
    modifier: Modifier = Modifier,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    BottomAppBar(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .height(AppDimens.Layout.BottomBarHeight),
        actions = {
            appBottomBarItems().forEach { item ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .clickable(
                                indication = ripple(
                                    bounded = false,
                                    radius = AppDimens.Ripple.Lg,
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
                                ),
                                interactionSource = remember {
                                    MutableInteractionSource()
                                }
                            ) {
                                onBottomActionClick(item.action)
                            },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Icon(
                            modifier = Modifier.size(AppDimens.Icon.Sm),
                            painter = item.icon,
                            contentDescription = null,
                            tint = Color.Gray
                        )
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun appBottomBarItems() : List<AppBottomBarItem> {
    return listOf(
        AppBottomBarItem(
            icon = AppIcons.Home,
            title = stringResource(R.string.navigate_to_home),
            action = AppBottomBarAction.HOME
        ),
        AppBottomBarItem(
            icon = AppIcons.Library,
            title = stringResource(R.string.navigate_to_library),
            action = AppBottomBarAction.LIBRARY
        ),
        AppBottomBarItem(
            icon = AppIcons.Discover,
            title = stringResource(R.string.navigate_to_discover),
            action = AppBottomBarAction.DISCOVERY
        ),
        AppBottomBarItem(
            icon = AppIcons.Settings,
            title = stringResource(R.string.navigate_to_settings),
            action = AppBottomBarAction.SETTINGS
        )
    )
}