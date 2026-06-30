package com.example.feature_artist.presentation.component

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
import com.example.core_model.Artist
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_ui.component.AppButton

@Composable
fun ArtistAction (
    artist: Artist,
    isFavoriteArtist: Boolean,
    onFollowClick: (String) -> Unit,
    onPlayClick: () -> Unit,
) {
    Row(horizontalArrangement = Arrangement.Center) {
        AppButton(
            modifier = Modifier.width(140.dp),
            title = if (isFavoriteArtist) stringResource(R.string.followed)
            else stringResource(R.string.follow),
            color = if (isFavoriteArtist) MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            else MaterialTheme.colorScheme.primary,
            border = if (isFavoriteArtist) BorderStroke(
                AppDimens.Border.Thin,
                MaterialTheme.colorScheme.onPrimary
            ) else null,
            onClick = { onFollowClick(artist.name) }
        )
        Spacer(modifier = Modifier.width(AppDimens.Space.Xl))
        AppButton(
            modifier = Modifier.width(140.dp),
            title = stringResource(R.string.action_play_music),
            color = MaterialTheme.colorScheme.primary,
            onClick = onPlayClick
        )
    }
}