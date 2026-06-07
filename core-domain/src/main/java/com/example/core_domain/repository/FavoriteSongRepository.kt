package com.example.core_domain.repository

import com.example.core_model.DisplaySong

interface FavoriteSongRepository {
    suspend fun getFavoriteSongs(): List<DisplaySong>
    suspend fun addSongToFavorite(songId: String)
    suspend fun removeSongFromFavorite(songId: String)
    suspend fun isFavoriteSong(songId: String): Boolean
}