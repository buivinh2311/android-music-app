package com.example.infrastructure.source.user

import com.example.core_database.dao.user.UserFavoriteArtistCrossRefDao
import com.example.core_database.datasource.user.FavoriteArtistLocalDataSource
import com.example.core_database.entity.artist.ArtistEntity
import com.example.core_database.entity.artist.FollowedArtistEntity
import com.example.core_database.entity.user.UserFavoriteArtistCrossRefEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteArtistLocalDataSourceImpl @Inject constructor(
    private val crossRefDao: UserFavoriteArtistCrossRefDao
): FavoriteArtistLocalDataSource {
    override suspend fun insert(userFavoriteArtist: UserFavoriteArtistCrossRefEntity) {
        crossRefDao.insert(userFavoriteArtist)
    }

    override fun isFavoriteArtist(userId: Int, artistName: String): Flow<Boolean> {
        return crossRefDao.isFavoriteArtist(userId, artistName)
    }

    override fun getFavoriteArtists(userId: Int): Flow<List<FollowedArtistEntity>> {
        return crossRefDao.getFavoriteArtists(userId)
    }

    override suspend fun delete(userId: Int, artistName: String) {
        crossRefDao.delete(userId, artistName)
    }


}