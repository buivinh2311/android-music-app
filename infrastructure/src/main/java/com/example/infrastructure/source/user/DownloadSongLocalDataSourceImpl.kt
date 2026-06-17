package com.example.infrastructure.source.user

import com.example.core_database.dao.user.UserDownloadSongCrossRefDao
import com.example.core_database.datasource.user.DownloadSongLocalDataSource
import com.example.core_database.entity.song.SongEntity
import com.example.core_database.entity.user.UserDownloadSongCrossRefEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DownloadSongLocalDataSourceImpl @Inject constructor(
    private val crossRefDao: UserDownloadSongCrossRefDao
): DownloadSongLocalDataSource {
    override suspend fun insert(userDownloadSong: UserDownloadSongCrossRefEntity) {
        crossRefDao.insert(userDownloadSong)
    }

    override fun isDownloadSong(userId: Int, songId: String): Flow<Boolean> {
        return crossRefDao.isDownloadSong(userId, songId)
    }

    override fun getDownloadSongs(userId: Int): Flow<List<SongEntity>> {
        return crossRefDao.getDownloadSongs(userId)
    }

    override fun getDownloadSongCount(userId: Int): Flow<Int> {
        return crossRefDao.getDownloadSongCount(userId)
    }

    override suspend fun delete(userId: Int, songId: String) {
        crossRefDao.delete(userId, songId)
    }
}