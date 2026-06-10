package com.example.core_database.datasource.user

import com.example.core_database.entity.song.SongEntity
import kotlinx.coroutines.flow.Flow

interface RecentSongLocalDataSource {
    fun getLimitRecentSongs(userId: Int, limit: Int): Flow<List<SongEntity>>
    fun getAllRecentSongs(userId: Int): Flow<List<SongEntity>>
    suspend fun insert(userId: Int, songId: String)
    suspend fun delete(userId: Int, songId: String)
    suspend fun clearAll(userId: Int)
}