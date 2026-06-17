package com.example.feature_library.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_resources.ui.icon.AppIcons

@Composable
fun LibraryCategory(
    modifier: Modifier = Modifier,
    favoriteSongCount: Int,
    downloadSongCount: Int,
    favoriteAlbumCount: Int,
    followedArtistCount: Int,
    onFavoriteClick: () -> Unit,
    onFavoriteAlbumClick: () -> Unit,
    onFollowedArtistClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = AppDimens.Space.Lg)
            .horizontalScroll(
                rememberScrollState()
            )
    ) {
        CategoryOption(
            painter = AppIcons.Favorite,
            title = stringResource(R.string.action_library_favorite),
            count = favoriteSongCount,
            tint = Color(0xFF1ABFDC),
            onClick = onFavoriteClick
        )
        Spacer(modifier = Modifier.width(AppDimens.Space.Lg))
        CategoryOption(
            painter = AppIcons.Download,
            title = stringResource(R.string.action_library_downloaded),
            count = downloadSongCount,
            tint = Color(0xFF7A43DA),
            onClick = onFavoriteClick
        )
        Spacer(modifier = Modifier.width(AppDimens.Space.Lg))
        CategoryOption(
            painter = AppIcons.Album,
            title = stringResource(R.string.action_library_album),
            count = favoriteAlbumCount,
            tint = Color(0xFFDA9F00),
            onClick = onFavoriteAlbumClick
        )
        Spacer(modifier = Modifier.width(AppDimens.Space.Lg))
        CategoryOption(
            painter = AppIcons.Artist,
            title = stringResource(R.string.action_library_artist),
            count = followedArtistCount,
            tint = Color(0xFFFC4433),
            onClick = onFollowedArtistClick
        )
        Spacer(modifier = Modifier.width(AppDimens.Space.Lg))
    }
}

@Composable
private fun CategoryOption(
    modifier: Modifier = Modifier,
    painter: Painter,
    title: String,
    count: Int,
    tint: Color = MaterialTheme.colorScheme.onBackground,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(AppDimens.Radius.Lg),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.9f)
        ),
        modifier = modifier
            .width(100.dp)
            .clip(RoundedCornerShape(AppDimens.Radius.Lg))
            .clickable {
                onClick()
            }
        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = AppDimens.Space.Lg),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painter,
                contentDescription = null,
                modifier = Modifier.size(AppDimens.Icon.Md),
                tint = tint
            )
            Spacer(modifier = Modifier.height(AppDimens.Space.Md))

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = count.toString(),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}