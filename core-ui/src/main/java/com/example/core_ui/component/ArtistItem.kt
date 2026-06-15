package com.example.core_ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import com.example.core_model.Artist
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens

@Composable
fun ArtistItem(
    modifier: Modifier = Modifier,
    artist: Artist,
    titleMinLines: Int,
    onArtistClick: (String) -> Unit
) {
    Column(
        modifier = modifier
            .clip(shape = RoundedCornerShape(AppDimens.Radius.Sm))
            .clickable {
                onArtistClick(artist.name)
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = artist.avatar,
            contentDescription = null,
            placeholder = painterResource(R.drawable.logo),
            error = painterResource(R.drawable.logo),
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(AppDimens.Radius.Sm)),
            contentScale = ContentScale.FillWidth
        )
        Spacer(modifier = Modifier.height(AppDimens.Space.Sm))
        Text(
            text = artist.name,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2,
            minLines = titleMinLines,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}