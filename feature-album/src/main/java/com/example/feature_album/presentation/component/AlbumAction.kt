package com.example.feature_album.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core_model.Album
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_ui.component.AppButton

@Composable
fun AlbumAction(
    modifier: Modifier = Modifier,
    album: Album,
    isFavoriteAlbum: Boolean,
    onFavoriteClick: (String) -> Unit
) {
    Row(horizontalArrangement = Arrangement.Center) {
        AppButton(
            modifier = modifier.width(140.dp),
            title = if (isFavoriteAlbum) stringResource(R.string.label_favorited)
            else stringResource(R.string.action_favorite),
            color = if (isFavoriteAlbum) MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            else MaterialTheme.colorScheme.primary,
            border = if (isFavoriteAlbum) BorderStroke(
                AppDimens.Border.Thin,
                MaterialTheme.colorScheme.onPrimary
            ) else null,
            onClick = { onFavoriteClick(album.name) }
        )
        Spacer(modifier = Modifier.width(AppDimens.Space.Xl))
        AppButton(
            modifier = Modifier.width(140.dp),
            title = stringResource(R.string.action_play_music),
            color = MaterialTheme.colorScheme.primary,
            onClick = {}
        )
    }
}