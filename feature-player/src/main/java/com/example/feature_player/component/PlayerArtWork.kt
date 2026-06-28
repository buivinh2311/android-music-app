package com.example.feature_player.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens

@Composable
fun PlayerArtWork(
    modifier: Modifier = Modifier,
    artworkUrl: String,
    isPlaying: Boolean,
    onArtworkClick: () -> Unit
) {
    val rotation = remember {
        Animatable(0f)
    }

    LaunchedEffect(isPlaying) {
        while (isPlaying) {
            rotation.animateTo(
                rotation.value + 360f,
                tween(
                    durationMillis = 20000,
                    easing = LinearEasing
                )
            )
        }
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Card(
            onClick = onArtworkClick,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onPrimary
            ),
            shape = CircleShape,
            border = BorderStroke(AppDimens.Border.Thick, Color.Gray),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .aspectRatio(1f)
                .rotate(rotation.value)
        ) {
            AsyncImage(
                model = artworkUrl,
                contentDescription = "Song Artwork",
                placeholder = painterResource(R.drawable.logo),
                error = painterResource(R.drawable.logo),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}