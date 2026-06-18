package com.example.feature_search.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_resources.ui.icon.AppIcons
import com.example.core_ui.component.AppIconButton

@Composable
fun SearchTopBar(
    onBackClick: () -> Unit,
    onQueryChange: (String) -> Unit
) {
    var query by remember {
        mutableStateOf("")
    }

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    Row(
        modifier = Modifier
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
        AppIconButton(
            painter = AppIcons.Back,
            contentDescription = stringResource(R.string.action_navigate_up),
            iconSize = AppDimens.Icon.Sm,
            rippleRadius = AppDimens.Ripple.Sm,
            tint = MaterialTheme.colorScheme.onBackground,
            rippleColor = MaterialTheme.colorScheme.onBackground,
            onClick = onBackClick
        )
        Spacer(modifier = Modifier.width(AppDimens.Space.Md))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(AppDimens.Radius.Xl))
                .background(MaterialTheme.colorScheme.onBackground.copy(0.05f))
                .padding(AppDimens.Space.Sm),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = AppIcons.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )

            Spacer(Modifier.width(8.dp))
            BasicTextField(
                value = query,
                onValueChange = {
                    query = it
                    onQueryChange(it)
                },
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground
                ),
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester),
                decorationBox = { innerTextField ->
                    Box {
                        if (query.isEmpty()) {
                            Text(
                                text = stringResource(R.string.hint_query),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                            )
                        }
                        innerTextField()
                    }
                }
            )
            }
    }
}