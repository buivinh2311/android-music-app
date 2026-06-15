package com.example.core_domain.repository

import com.example.core_model.Song
import kotlinx.coroutines.flow.Flow

interface RecentSongRepository {
    fun getLimitRecentSongs(limit: Int): Flow<List<Song>>
    fun getAllRecentSongs(): Flow<List<Song>>
    suspend fun insert(songId: String)
    suspend fun delete(songId: String)
    suspend fun clearAll()
}