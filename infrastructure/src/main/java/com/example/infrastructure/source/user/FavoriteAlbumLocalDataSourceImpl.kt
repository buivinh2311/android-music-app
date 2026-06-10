package com.example.infrastructure.source.user

import com.example.core_database.dao.user.UserFavoriteAlbumCrossRefDao
import com.example.core_database.datasource.user.FavoriteAlbumLocalDataSource
import com.example.core_database.entity.album.AlbumEntity
import com.example.core_database.entity.user.UserFavoriteAlbumCrossRefEntity
import javax.inject.Inject

class FavoriteAlbumLocalDataSourceImpl @Inject constructor(
    private val crossRefDao: UserFavoriteAlbumCrossRefDao
): FavoriteAlbumLocalDataSource {
    override suspend fun insert(userFavoriteAlbum: UserFavoriteAlbumCrossRefEntity) {
        crossRefDao.insert(userFavoriteAlbum)
    }

    override suspend fun isFavoriteAlbum(userId: Int, albumId: Int): Boolean {
        return crossRefDao.isFavoriteAlbum(userId, albumId)
    }

    override suspend fun getFavoriteAlbums(userId: Int): List<AlbumEntity> {
        return crossRefDao.getFavoriteAlbums(userId)
    }

    override suspend fun delete(userId: Int, albumId: Int) {
        crossRefDao.delete(userId, albumId)
    }


}