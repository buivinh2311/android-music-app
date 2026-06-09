package com.example.core_domain.repository

import com.example.core_model.DisplaySong
import kotlinx.coroutines.flow.Flow

interface FavoriteSongRepository {
    suspend fun getFavoriteSongs(): List<DisplaySong>
    suspend fun addSongToFavorite(songId: String)
    suspend fun removeSongFromFavorite(songId: String)
    fun isFavoriteSong(songId: String): Flow<Boolean>
}