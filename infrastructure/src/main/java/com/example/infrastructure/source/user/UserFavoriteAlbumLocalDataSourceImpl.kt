package com.example.infrastructure.source.user

import com.example.core_database.dao.user.UserFavoriteAlbumCrossRefDao
import com.example.core_database.datasource.user.UserFavoriteAlbumLocalDataSource
import com.example.core_database.entity.album.AlbumEntity
import com.example.core_database.entity.user.UserFavoriteAlbumCrossRefEntity
import javax.inject.Inject

class UserFavoriteAlbumLocalDataSourceImpl @Inject constructor(
    private val userFavoriteAlbumCrossRefDao: UserFavoriteAlbumCrossRefDao
): UserFavoriteAlbumLocalDataSource {
    override suspend fun insert(userFavoriteAlbum: UserFavoriteAlbumCrossRefEntity) {
        userFavoriteAlbumCrossRefDao.insert(userFavoriteAlbum)
    }

    override suspend fun isFavoriteAlbum(userId: Int, albumId: Int): Boolean {
        return userFavoriteAlbumCrossRefDao.isFavoriteAlbum(userId, albumId)
    }

    override suspend fun getFavoriteAlbums(userId: Int): List<AlbumEntity> {
        return userFavoriteAlbumCrossRefDao.getFavoriteAlbums(userId)
    }

    override suspend fun delete(userId: Int, albumId: Int) {
        userFavoriteAlbumCrossRefDao.delete(userId, albumId)
    }


}