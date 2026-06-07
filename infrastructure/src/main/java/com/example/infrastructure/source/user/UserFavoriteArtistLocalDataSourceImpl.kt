package com.example.infrastructure.source.user

import com.example.core_database.dao.user.UserFavoriteArtistCrossRefDao
import com.example.core_database.datasource.user.UserFavoriteArtistLocalDataSource
import com.example.core_database.entity.artist.ArtistEntity
import com.example.core_database.entity.user.UserFavoriteArtistCrossRefEntity
import javax.inject.Inject

class UserFavoriteArtistLocalDataSourceImpl @Inject constructor(
    private val userFavoriteArtistCrossRefDao: UserFavoriteArtistCrossRefDao
): UserFavoriteArtistLocalDataSource {
    override suspend fun insert(userFavoriteArtist: UserFavoriteArtistCrossRefEntity) {
        userFavoriteArtistCrossRefDao.insert(userFavoriteArtist)
    }

    override suspend fun isFavoriteArtist(userId: Int, artistId: Int): Boolean {
        return userFavoriteArtistCrossRefDao.isFavoriteArtist(userId, artistId)
    }

    override suspend fun getFavoriteArtists(userId: Int): List<ArtistEntity> {
        return userFavoriteArtistCrossRefDao.getFavoriteArtists(userId)
    }

    override suspend fun delete(userId: Int, artistId: Int) {
        userFavoriteArtistCrossRefDao.delete(userId, artistId)
    }


}