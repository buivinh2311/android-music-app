package com.example.infrastructure.source.user

import com.example.core_database.dao.user.UserDownloadSongCrossRefDao
import com.example.core_database.datasource.user.UserDownloadSongLocalDataSource
import com.example.core_database.entity.song.SongEntity
import com.example.core_database.entity.user.UserDownloadSongCrossRefEntity
import javax.inject.Inject

class UserDownloadSongLocalDataSourceImpl @Inject constructor(
    private val userDownloadSongCrossRefDao: UserDownloadSongCrossRefDao
): UserDownloadSongLocalDataSource {
    override suspend fun insert(userDownloadSong: UserDownloadSongCrossRefEntity) {
        userDownloadSongCrossRefDao.insert(userDownloadSong)
    }

    override suspend fun isDownloadSong(userId: Int, songId: String): Boolean {
        return userDownloadSongCrossRefDao.isDownloadSong(userId, songId)
    }

    override suspend fun getDownloadSongs(userId: Int): List<SongEntity> {
        return userDownloadSongCrossRefDao.getDownloadSongs(userId)
    }

    override suspend fun delete(userId: Int, songId: String) {
        userDownloadSongCrossRefDao.delete(userId, songId)
    }


}