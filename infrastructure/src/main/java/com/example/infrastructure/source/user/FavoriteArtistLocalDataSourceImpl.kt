package com.example.infrastructure.source.user

import com.example.core_database.dao.user.UserFavoriteArtistCrossRefDao
import com.example.core_database.datasource.user.FavoriteArtistLocalDataSource
import com.example.core_database.entity.artist.ArtistEntity
import com.example.core_database.entity.user.UserFavoriteArtistCrossRefEntity
import javax.inject.Inject

class FavoriteArtistLocalDataSourceImpl @Inject constructor(
    private val crossRefDao: UserFavoriteArtistCrossRefDao
): FavoriteArtistLocalDataSource {
    override suspend fun insert(userFavoriteArtist: UserFavoriteArtistCrossRefEntity) {
        crossRefDao.insert(userFavoriteArtist)
    }

    override suspend fun isFavoriteArtist(userId: Int, artistId: Int): Boolean {
        return crossRefDao.isFavoriteArtist(userId, artistId)
    }

    override suspend fun getFavoriteArtists(userId: Int): List<ArtistEntity> {
        return crossRefDao.getFavoriteArtists(userId)
    }

    override suspend fun delete(userId: Int, artistId: Int) {
        crossRefDao.delete(userId, artistId)
    }


}