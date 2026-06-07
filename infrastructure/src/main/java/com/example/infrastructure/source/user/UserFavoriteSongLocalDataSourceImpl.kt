package com.example.infrastructure.source.user

import com.example.core_database.dao.user.UserFavoriteSongCrossRefDao
import com.example.core_database.datasource.user.UserFavoriteSongLocalDataSource
import com.example.core_database.entity.song.SongEntity
import com.example.core_database.entity.user.UserFavoriteSongCrossRefEntity
import javax.inject.Inject

class UserFavoriteSongLocalDataSourceImpl @Inject constructor(
    private val userFavoriteSongCrossRefDao: UserFavoriteSongCrossRefDao
): UserFavoriteSongLocalDataSource {
    override suspend fun insert(userFavoriteSong: UserFavoriteSongCrossRefEntity) {
        userFavoriteSongCrossRefDao.insert(userFavoriteSong)
    }

    override suspend fun isFavoriteSong(userId: Int, songId: String): Boolean {
        return userFavoriteSongCrossRefDao.isFavoriteSong(userId, songId)
    }

    override suspend fun getFavoriteSongs(userId: Int): List<SongEntity> {
        return userFavoriteSongCrossRefDao.getFavoriteSong(userId)
    }

    override suspend fun delete(userId: Int, songId: String) {
        userFavoriteSongCrossRefDao.delete(userId, songId)
    }


}