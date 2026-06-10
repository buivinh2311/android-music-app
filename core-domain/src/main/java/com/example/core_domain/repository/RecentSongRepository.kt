package com.example.core_domain.repository

import com.example.core_model.DisplaySong
import kotlinx.coroutines.flow.Flow

interface RecentSongRepository {
    fun getLimitRecentSongs(limit: Int): Flow<List<DisplaySong>>
    fun getAllRecentSongs(): Flow<List<DisplaySong>>
    suspend fun insert(songId: String)
    suspend fun delete(songId: String)
    suspend fun clearAll()
}