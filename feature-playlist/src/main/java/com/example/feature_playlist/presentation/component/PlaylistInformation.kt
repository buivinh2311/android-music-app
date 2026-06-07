package com.example.feature_playlist.presentation.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens

@Composable
fun PlaylistInformation(
    model: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = model,
        contentDescription = "playlist avatar",
        placeholder = painterResource(R.drawable.ic_music_note),
        error = painterResource(R.drawable.ic_music_not_available),
        modifier = Modifier.size(200.dp)
    )
    Spacer(modifier = Modifier.height(AppDimens.Space.Lg))
    Text(
        text = "ten playlist",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
    )
    Text(
        text = "so bai hat",
        style = MaterialTheme.typography.bodyLarge
    )
}