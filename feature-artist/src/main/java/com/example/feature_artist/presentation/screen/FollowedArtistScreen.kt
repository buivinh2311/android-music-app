package com.example.feature_artist.presentation.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_ui.component.AppBottomBar
import com.example.core_ui.component.AppTopBar
import com.example.core_ui.menu.AppBottomBarAction
import com.example.core_utils.util.ArtistUtil
import com.example.feature_artist.presentation.viewmodel.ArtistChooserViewModel
import com.example.feature_artist.presentation.viewmodel.FollowedArtistViewModel

@Composable
fun FollowedArtistScreen(
    onArtistClick: (String) -> Unit,
    onBackClick: () -> Unit,
    onBottomActionClick: (AppBottomBarAction) -> Unit
) {
    val followedArtistViewModel: FollowedArtistViewModel = hiltViewModel()
    val followedArtists by followedArtistViewModel
        .followedArtists.collectAsStateWithLifecycle(emptyList())
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AppBottomBar(onBottomActionClick = onBottomActionClick)
        },
        topBar = {
            AppTopBar(
                title = stringResource(R.string.title_artist),
                onBackClick = onBackClick
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(vertical = AppDimens.Space.Md),
            contentPadding = PaddingValues(
                vertical = AppDimens.Space.Lg
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = stringResource(R.string.title_artist),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(AppDimens.Space.Xs))
                Text(
                    text = followedArtists.size.toString() + " " +
                            stringResource(R.string.title_followed_artist),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(AppDimens.Space.Xl))
            }

            items(
                count = followedArtists.size,
                key = { index -> followedArtists[index].name }
            ) { index ->
                val artist = followedArtists[index]
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppDimens.Space.Xs)
                        .clip(RoundedCornerShape(AppDimens.Radius.Sm))
                        .clickable {
                            onArtistClick(artist.name)
                        }
                        .padding(
                            vertical = AppDimens.Space.Sm,
                            horizontal = AppDimens.Space.Md
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = artist.avatar,
                        contentDescription = "avatar image",
                        placeholder = painterResource(R.drawable.ic_artist),
                        error = painterResource(R.drawable.ic_artist),
                        modifier = Modifier
                            .size(AppDimens.ImageSize.Md)
                            .clip(shape = CircleShape)
                            .background(
                                color = MaterialTheme.colorScheme.background,
                                shape = CircleShape
                            )
                            .border(
                                BorderStroke(
                                    width = AppDimens.Border.Thin,
                                    color = MaterialTheme.colorScheme.onBackground
                                ), shape = CircleShape
                            ),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.width(AppDimens.Space.Md))
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = artist.name,
                            style = MaterialTheme.typography.titleSmall,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Spacer(modifier = Modifier.height(AppDimens.Space.Xs))
                        Text(
                            text = ArtistUtil.interestedToString(artist.interested + 1) +
                                    stringResource(R.string.text_interested),
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}