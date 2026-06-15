package com.example.feature_playlist.presentation.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.core_model.Playlist
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens

@Composable
fun PlaylistInformation(
    playlist: Playlist
) {
    AsyncImage(
        model = playlist.artwork,
        contentDescription = "playlist avatar",
        placeholder = painterResource(R.drawable.logo),
        error = painterResource(R.drawable.logo),
        modifier = Modifier
            .size(200.dp)
            .clip(RoundedCornerShape(AppDimens.Radius.Lg))
    )
    Spacer(modifier = Modifier.height(AppDimens.Space.Lg))
    Text(
        text = playlist.name,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(AppDimens.Space.Xs))
    Text(
        text = playlist.size.toString() + stringResource(R.string.text_song),
        style = MaterialTheme.typography.bodyLarge
    )
}