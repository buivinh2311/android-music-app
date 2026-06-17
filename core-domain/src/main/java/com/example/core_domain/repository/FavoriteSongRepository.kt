package com.example.core_domain.repository

import com.example.core_model.Song
import kotlinx.coroutines.flow.Flow

interface FavoriteSongRepository {
    fun getFavoriteSongs(): Flow<List<Song>>
    suspend fun addSongToFavorite(songId: String)
    suspend fun removeSongFromFavorite(songId: String)
    fun isFavoriteSong(songId: String): Flow<Boolean>
    fun getFavoriteSongCount(): Flow<Int>
}