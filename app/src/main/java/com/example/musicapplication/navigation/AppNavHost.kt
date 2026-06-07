package com.example.musicapplication.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.core_ui.data.AppBottomBarAction
import com.example.core_ui.data.SongOptionAction
import com.example.core_ui.data.SongOptionItem
import com.example.musicapplication.navigation.route.AlbumDetailRoute
import com.example.musicapplication.navigation.route.AlbumRoute
import com.example.musicapplication.navigation.route.ArtistChooserRoute
import com.example.musicapplication.navigation.route.ArtistDetailRoute
import com.example.musicapplication.navigation.route.ArtistRoute
import com.example.musicapplication.navigation.route.DiscoveryRoute
import com.example.musicapplication.navigation.route.FavoriteRoute
import com.example.musicapplication.navigation.route.ForYouRoute
import com.example.musicapplication.navigation.route.HomeRoute
import com.example.musicapplication.navigation.route.LibraryRoute
import com.example.musicapplication.navigation.route.MostListenedRoute
import com.example.musicapplication.navigation.route.PlayerRoute
import com.example.musicapplication.navigation.route.PlaylistDetailRoute
import com.example.musicapplication.navigation.route.PlaylistRoute
import com.example.musicapplication.navigation.route.RecentRoute
import com.example.musicapplication.navigation.route.RecommendedRoute

@Composable
fun AppNavHost() {
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
        when(action) {
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

    val onSongOptionClick: (SongOptionItem) -> Unit = { item ->
        when(item.action) {
            SongOptionAction.DOWNLOAD -> {

            }

            SongOptionAction.ADD_TO_LIBRARY -> {

            }

            SongOptionAction.ADD_TO_PLAYLIST -> {

            }

            SongOptionAction.VIEW_ALBUM -> {
                navController.navigate("${AppRoute.ALBUM_DETAIL}/${item.album}")
            }

            SongOptionAction.VIEW_ARTIST -> {
                val artistStr = item.artist
                if(artistStr != null && artistStr.contains(" ft ")) {
                    navController.navigate("${AppRoute.ARTIST_CHOOSER}/${item.artist}")
                } else {
                    navController.navigate("${AppRoute.ARTIST_DETAIL}/${item.artist}")
                }
            }

            SongOptionAction.SIMILAR_CONTENT -> {

            }

            SongOptionAction.RING_TONE -> {

            }

            SongOptionAction.EQUALIZER -> {

            }

            SongOptionAction.COMMENT -> {

            }

            SongOptionAction.BLOCK -> {

            }

            SongOptionAction.REPORT -> {

            }
        }
    }

    NavHost(navController = navController, startDestination = AppRoute.HOME) {
        composable(AppRoute.HOME) {
            HomeRoute(
                navController = navController,
                onSearchClick = onSearchClick,
                onSongClick = onSongClick,
                onBottomActionClick = onBottomActionClick,
                onSongOptionClick = onSongOptionClick
            )
        }

        composable(AppRoute.LIBRARY) {
            LibraryRoute(
                navController = navController,
                onSearchClick = onSearchClick,
                onSongClick = onSongClick,
                onBottomActionClick = onBottomActionClick,
                onSongOptionClick = onSongOptionClick
            )
        }

        composable(AppRoute.DISCOVERY) {
            DiscoveryRoute(
                navController = navController,
                onSearchClick = onSearchClick,
                onSongClick = onSongClick,
                onBottomActionClick = onBottomActionClick,
                onSongOptionClick = onSongOptionClick
            )
        }

////        composable(AppRoute.SETTINGS) {
////            navController = navController,
////            onBottomActionClick = onBottomActionClick
////        }
//
//        composable(AppRoute) {  }

        composable(AppRoute.ALBUM) {
            AlbumRoute(
                navController = navController,
                onBottomActionClick = onBottomActionClick,
                onBackClick = onBackClick
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
                    onSongOptionClick = onSongOptionClick
                )
            }
        }

        composable(AppRoute.RECOMMENDED) {
            RecommendedRoute(
                navController = navController,
                onBackClick = onBackClick,
                onBottomActionClick = onBottomActionClick,
                onSongOptionClick = onSongOptionClick
            )
        }

        composable(AppRoute.FAVORITE) {
            FavoriteRoute(
                navController = navController,
                onBackClick = onBackClick,
                onBottomActionClick = onBottomActionClick,
                onSongOptionClick = onSongOptionClick
            )
        }

        composable(AppRoute.RECENT) {
            RecentRoute(
                navController = navController,
                onBackClick = onBackClick,
                onBottomActionClick = onBottomActionClick,
                onSongOptionClick = onSongOptionClick
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
        ) {
            PlaylistDetailRoute(
                navController = navController,
                onBackClick = onBackClick,
                onBottomActionClick = onBottomActionClick,
                onSongOptionClick = onSongOptionClick
            )
        }

        composable(AppRoute.ARTIST) {
            ArtistRoute(
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
                    onSongOptionClick = onSongOptionClick
                )
            }
        }

        composable(AppRoute.MOST_LISTENED) {
            MostListenedRoute(
                navController = navController,
                onBackClick = onBackClick,
                onBottomActionClick = onBottomActionClick,
                onSongOptionClick = onSongOptionClick
            )
        }

        composable(AppRoute.FOR_YOU) {
            ForYouRoute(
                navController = navController,
                onBackClick = onBackClick,
                onBottomActionClick = onBottomActionClick,
                onSongOptionClick = onSongOptionClick
            )
        }

        composable(AppRoute.PLAYER_WITH_ARG) { backStackEntry ->
            val songId = backStackEntry.arguments?.getString("songId")
            songId?.let {
                PlayerRoute(
                    songId = songId,
                    onBackClick = onBackClick,
                    onSongOptionClick = onSongOptionClick
                )
            }
        }
    }
}