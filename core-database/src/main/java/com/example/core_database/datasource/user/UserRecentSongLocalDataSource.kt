package com.example.core_database.datasource.user

import com.example.core_database.entity.song.SongEntity
import com.example.core_database.entity.user.UserRecentSongCrossRefEntity

interface UserRecentSongLocalDataSource {
    fun getMostRecentSong(userId: Int): SongEntity?
    suspend fun getUserRecentSongById(songId: String, userId: Int): UserRecentSongCrossRefEntity?
    fun getRecentSongs(userId: Int): List<SongEntity>
    fun getMostListenedSongIdsForUser(userId: Int, limit: Int): List<String>
    suspend fun insert(crossRef: UserRecentSongCrossRefEntity)
    suspend fun update(songId: String, userId: Int, updatedAt: Long)
    suspend fun delete(crossRef: UserRecentSongCrossRefEntity)
    suspend fun clearAll()
}