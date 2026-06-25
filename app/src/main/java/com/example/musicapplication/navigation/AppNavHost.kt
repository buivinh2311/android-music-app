package com.example.musicapplication.navigation

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.core_model.Song
import com.example.core_resources.R
import com.example.core_resources.ui.dimen.AppDimens
import com.example.core_ui.component.NetworkBanner
import com.example.core_ui.component.showToast
import com.example.core_ui.menu.AppBottomBarAction
import com.example.musicapplication.navigation.route.AlbumDetailRoute
import com.example.musicapplication.navigation.route.AlbumRoute
import com.example.musicapplication.navigation.route.ArtistChooserRoute
import com.example.musicapplication.navigation.route.ArtistDetailRoute
import com.example.musicapplication.navigation.route.ArtistRoute
import com.example.musicapplication.navigation.route.DiscoveryRoute
import com.example.musicapplication.navigation.route.FavoriteAlbumRoute
import com.example.musicapplication.navigation.route.FavoriteRoute
import com.example.musicapplication.navigation.route.FollowedArtistRoute
import com.example.musicapplication.navigation.route.ForYouRoute
import com.example.musicapplication.navigation.route.HomeRoute
import com.example.musicapplication.navigation.route.LibraryRoute
import com.example.musicapplication.navigation.route.MostListenedRoute
import com.example.musicapplication.navigation.route.PlayerRoute
import com.example.musicapplication.navigation.route.PlaylistDetailRoute
import com.example.musicapplication.navigation.route.PlaylistRoute
import com.example.musicapplication.navigation.route.RecentRoute
import com.example.musicapplication.navigation.route.RecommendedRoute
import com.example.musicapplication.navigation.route.SearchRoute
import com.example.musicapplication.navigation.route.SettingsRoute
import com.example.shared_presentation.menu.SongOptionAction
import com.example.shared_presentation.menu.SongOptionItem
import com.example.shared_presentation.presentation.MiniPlayer

@Composable
fun AppNavHost(
    currentSong: Song?,
    isFavoriteSong: Boolean,
    isPlaying: Boolean,
    onFavoriteClick: (Song) -> Unit,
    onTogglePlayClick: () -> Unit,
    onNextClick: () -> Unit
) {
    val navController = rememberNavController()
    val onSongClick: (String) -> Unit = { songId ->
        navController.navigate("${AppRoute.PLAYER}/$songId")
    }
    val onVoiceSearchClick: () -> Unit = {
        navController.navigate(AppRoute.SEARCH)
    }
    val onSearchClick: () -> Unit = {
        navController.navigate(AppRoute.SEARCH)
    }
    val onBackClick: () -> Unit = {
        navController.navigateUp()
    }
    val onBottomActionClick: (AppBottomBarAction) -> Unit = { action ->
        when (action) {
            AppBottomBarAction.HOME -> {
                navController.navigate(AppRoute.HOME)
            }

            AppBottomBarAction.LIBRARY -> {
                navController.navigate(AppRoute.LIBRARY)
            }

            AppBottomBarAction.DISCOVERY -> {
                navController.navigate(AppRoute.DISCOVERY)
            }

            AppBottomBarAction.SETTINGS -> {
                navController.navigate(AppRoute.SETTINGS)
            }
        }
    }

    val onSongNavigationAction: (SongOptionItem) -> Unit = { item ->
        when (item.action) {
            SongOptionAction.VIEW_ALBUM -> {
                val encodedName = Uri.encode(item.album)
                navController.navigate("${AppRoute.ALBUM_DETAIL}/${encodedName}")
            }

            SongOptionAction.VIEW_ARTIST -> {
                val artistStr = item.artist
                if (artistStr != null && artistStr.contains(" ft ")) {
                    navController.navigate("${AppRoute.ARTIST_CHOOSER}/${item.artist}")
                } else {
                    navController.navigate("${AppRoute.ARTIST_DETAIL}/${item.artist}")
                }
            }

            SongOptionAction.COMMENT -> {

            }

            else -> {}
        }
    }

    NavHost(navController = navController, startDestination = AppRoute.HOME) {
        composable(AppRoute.HOME) {
            HomeRoute(
                navController = navController,
                onSearchClick = onSearchClick,
                onSongClick = onSongClick,
                onBottomActionClick = onBottomActionClick,
                onSongNavigationAction = onSongNavigationAction
            )
        }

        composable(AppRoute.LIBRARY) {
            LibraryRoute(
                navController = navController,
                onSearchClick = onSearchClick,
                onSongClick = onSongClick,
                onBottomActionClick = onBottomActionClick,
                onSongNavigationAction = onSongNavigationAction
            )
        }

        composable(AppRoute.DISCOVERY) {
            DiscoveryRoute(
                navController = navController,
                onSearchClick = onSearchClick,
                onSongClick = onSongClick,
                onBottomActionClick = onBottomActionClick,
                onSongNavigationAction = onSongNavigationAction
            )
        }

        composable(AppRoute.SETTINGS) {
            SettingsRoute(
                onSearchClick = onSearchClick,
                onBottomActionClick = onBottomActionClick
            )
        }

        composable(AppRoute.SEARCH) {
            SearchRoute(
                navController = navController,
                onSongClick = onSongClick,
                onBackClick = onBackClick,
                onBottomActionClick = onBottomActionClick,
                onSongNavigationAction = onSongNavigationAction
            )
        }

        composable(AppRoute.ALBUM) {
            AlbumRoute(
                navController = navController,
                onBottomActionClick = onBottomActionClick,
                onBackClick = onBackClick
            )
        }

        composable(AppRoute.FAVORITE_ALBUM) {
            FavoriteAlbumRoute(
                navController = navController,
                onBackClick = onBackClick,
                onBottomActionClick = onBottomActionClick
            )
        }

        composable(AppRoute.ALBUM_DETAIL_WITH_ARG) { backStackEntry ->
            val albumName = backStackEntry.arguments?.getString("albumName")
            albumName?.let {
                AlbumDetailRoute(
                    navController = navController,
                    albumName = albumName,
                    onBackClick = onBackClick,
                    onBottomActionClick = onBottomActionClick,
                    onSongNavigationAction = onSongNavigationAction
                )
            }
        }

        composable(AppRoute.RECOMMENDED) {
            RecommendedRoute(
                navController = navController,
                onBackClick = onBackClick,
                onBottomActionClick = onBottomActionClick,
                onSongNavigationAction = onSongNavigationAction
            )
        }

        composable(AppRoute.FAVORITE) {
            FavoriteRoute(
                navController = navController,
                onBackClick = onBackClick,
                onBottomActionClick = onBottomActionClick,
                onSongNavigationAction = onSongNavigationAction
            )
        }

        composable(AppRoute.RECENT) {
            RecentRoute(
                navController = navController,
                onBackClick = onBackClick,
                onBottomActionClick = onBottomActionClick,
                onSongNavigationAction = onSongNavigationAction
            )
        }

        composable(AppRoute.PLAYLIST) {
            PlaylistRoute(
                navController = navController,
                onBackClick = onBackClick,
                onBottomActionClick = onBottomActionClick
            )
        }

        composable(
            AppRoute.PLAYLIST_DETAIL_WITH_ARG,
            arguments = listOf(
                navArgument("playlistId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val playlistId = backStackEntry.arguments?.getInt("playlistId")
            playlistId?.let {
                PlaylistDetailRoute(
                    playlistId = playlistId,
                    navController = navController,
                    onBackClick = onBackClick,
                    onBottomActionClick = onBottomActionClick,
                    onSongNavigationAction = onSongNavigationAction
                )
            }
        }

        composable(AppRoute.ARTIST) {
            ArtistRoute(
                navController = navController,
                onBackClick = onBackClick,
                onBottomActionClick = onBottomActionClick
            )
        }

        composable(AppRoute.FOLLOWED_ARTIST) {
            FollowedArtistRoute(
                navController = navController,
                onBackClick = onBackClick,
                onBottomActionClick = onBottomActionClick
            )
        }

        composable(AppRoute.ARTIST_CHOOSER_WITH_ARG) { backStackEntry ->
            val artistStr = backStackEntry.arguments?.getString("artistStr")
            artistStr?.let {
                ArtistChooserRoute(
                    artistStr = artistStr,
                    navController = navController,
                    onBackClick = onBackClick,
                    onBottomActionClick = onBottomActionClick
                )
            }
        }

        composable(AppRoute.ARTIST_DETAIL_WITH_ARG) { backStackEntry ->
            val artistName = backStackEntry.arguments?.getString("artistName")
            artistName?.let {
                ArtistDetailRoute(
                    artistName = artistName,
                    navController = navController,
                    onBackClick = onBackClick,
                    onBottomActionClick = onBottomActionClick,
                    onSongNavigationAction = onSongNavigationAction
                )
            }
        }

        composable(AppRoute.MOST_LISTENED) {
            MostListenedRoute(
                navController = navController,
                onBackClick = onBackClick,
                onBottomActionClick = onBottomActionClick,
                onSongNavigationAction = onSongNavigationAction
            )
        }

        composable(AppRoute.FOR_YOU) {
            ForYouRoute(
                navController = navController,
                onBackClick = onBackClick,
                onBottomActionClick = onBottomActionClick,
                onSongNavigationAction = onSongNavigationAction
            )
        }

        composable(
            AppRoute.PLAYER_WITH_ARG,
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(900)
                )
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(900)
                )
            }
        ) { backStackEntry ->
            val songId = backStackEntry.arguments?.getString("songId")
            songId?.let {
                PlayerRoute(
                    songId = songId,
                    onBackClick = onBackClick,
                    onSongNavigationAction = onSongNavigationAction
                )
            }
        }
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showMiniPlayer = currentRoute !in setOf(
        AppRoute.PLAYER_WITH_ARG
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        NetworkBanner(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(
                    bottom = AppDimens.Layout.BottomBarHeight +
                            AppDimens.Layout.MiniPlayerHeight
                )
                .align(Alignment.BottomCenter)
        )
    }

    AnimatedVisibility(
        visible = showMiniPlayer,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        currentSong?.let {
            Box(
                Modifier.fillMaxSize()
            ) {
                MiniPlayer(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .padding(bottom = AppDimens.Layout.BottomBarHeight)
                        .align(Alignment.BottomCenter),
                    song = currentSong,
                    isFavoriteSong = isFavoriteSong,
                    isPlaying = isPlaying,
                    onMiniPlayerClick = {
                        navController.navigate("${AppRoute.PLAYER}/${currentSong.id}")
                    },
                    onFavoriteClick = {
                        onFavoriteClick(currentSong)
                    },
                    onTogglePlayClick = onTogglePlayClick,
                    onNextClick = onNextClick
                )
            }
        }
    }
}