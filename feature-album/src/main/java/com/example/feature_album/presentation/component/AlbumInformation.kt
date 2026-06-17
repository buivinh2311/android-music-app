package com.example.feature_album.presentation.component

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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.core_model.Album
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens

@Composable
fun AlbumInformation(
    album: Album
) {
    AsyncImage(
        model = album.artworkUrl,
        contentDescription = "album avatar",
        placeholder = painterResource(R.drawable.logo),
        error = painterResource(R.drawable.logo),
        modifier = Modifier
            .size(220.dp)
            .clip(shape = RoundedCornerShape(AppDimens.Space.Lg))
    )
    Spacer(modifier = Modifier.height(AppDimens.Space.Md))
    Text(
        text = album.name,
        style = MaterialTheme.typography.titleLarge
    )
    Text(
        text = album.size.toString() + stringResource(R.string.text_song),
        style = MaterialTheme.typography.bodyLarge
    )
}