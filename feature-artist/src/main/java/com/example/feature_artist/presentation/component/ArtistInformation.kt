package com.example.feature_artist.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.core_model.Artist
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_utils.util.ArtistUtil

@Composable
fun ArtistInformation(
    modifier: Modifier = Modifier,
    artist: Artist
) {
    AsyncImage(
        model = artist.avatar,
        contentDescription = "artist avatar",
        placeholder = painterResource(R.drawable.ic_artist),
        error = painterResource(R.drawable.ic_artist),
        modifier = Modifier
            .size(220.dp)
            .clip(shape = RoundedCornerShape(AppDimens.Radius.Lg)),
        contentScale = ContentScale.Crop
    )
    Spacer(modifier = Modifier.height(AppDimens.Space.Md))
    Text(
        text = artist.name,
        style = MaterialTheme.typography.titleLarge
    )
    Text(
        text = ArtistUtil.interestedToString(artist.interested) +
                stringResource(R.string.text_interested),
        style = MaterialTheme.typography.bodyLarge
    )
    Spacer(modifier = Modifier.height(AppDimens.Space.Md))
    Row(horizontalArrangement = Arrangement.Center) {
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier.width(130.dp)
        ) {
            Text(
                text = stringResource(R.string.follow),
                style = MaterialTheme.typography.labelLarge
            )
        }
        Spacer(modifier = Modifier.width(AppDimens.Space.Xl))
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier.width(130.dp)
        ) {
            Text(
                text = stringResource(R.string.action_play_music),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}