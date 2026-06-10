package com.example.infrastructure.source.user

import com.example.core_database.dao.user.UserRecentSongCrossRefDao
import com.example.core_database.datasource.user.RecentSongLocalDataSource
import com.example.core_database.entity.song.SongEntity
import com.example.core_database.entity.user.UserRecentSongCrossRefEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecentSongLocalDataSourceImpl @Inject constructor(
    private val crossRefDao: UserRecentSongCrossRefDao
): RecentSongLocalDataSource {
    override fun getLimitRecentSongs(
        userId: Int,
        limit: Int
    ): Flow<List<SongEntity>> {
        return crossRefDao.getLimitRecentSongs(userId, limit)
    }

    override fun getAllRecentSongs(userId: Int): Flow<List<SongEntity>> {
        return crossRefDao.getAllRecentSongs(userId)
    }

    override suspend fun insert(userId: Int, songId: String) {
        crossRefDao.insert(UserRecentSongCrossRefEntity(songId, userId))
    }

    override suspend fun delete(userId: Int, songId: String) {
        crossRefDao.delete(UserRecentSongCrossRefEntity(songId, userId))
    }

    override suspend fun clearAll(userId: Int) {
        crossRefDao.clearAll(userId)
    }

}