package com.example.infrastructure.source.user

import com.example.core_database.dao.user.UserFavoriteAlbumCrossRefDao
import com.example.core_database.datasource.user.FavoriteAlbumLocalDataSource
import com.example.core_database.entity.album.AlbumEntity
import com.example.core_database.entity.user.UserFavoriteAlbumCrossRefEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteAlbumLocalDataSourceImpl @Inject constructor(
    private val crossRefDao: UserFavoriteAlbumCrossRefDao
): FavoriteAlbumLocalDataSource {
    override suspend fun insert(userFavoriteAlbum: UserFavoriteAlbumCrossRefEntity) {
        crossRefDao.insert(userFavoriteAlbum)
    }

    override fun isFavoriteAlbum(userId: Int, albumName: String): Flow<Boolean> {
        return crossRefDao.isFavoriteAlbum(userId, albumName)
    }

    override fun getFavoriteAlbums(userId: Int): Flow<List<AlbumEntity>> {
        return crossRefDao.getFavoriteAlbums(userId)
    }

    override fun getFavoriteAlbumCount(userId: Int): Flow<Int> {
        return crossRefDao.getFavoriteAlbumCount(userId)
    }

    override suspend fun delete(userId: Int, albumName: String) {
        crossRefDao.delete(userId, albumName)
    }

}