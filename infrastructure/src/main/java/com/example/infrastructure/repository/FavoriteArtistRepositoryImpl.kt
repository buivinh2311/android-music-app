package com.example.infrastructure.repository

import com.example.core_database.datasource.user.FavoriteArtistLocalDataSource
import com.example.core_database.entity.user.UserFavoriteArtistCrossRefEntity
import com.example.core_domain.manager.UserManager
import com.example.core_domain.repository.FavoriteArtistRepository
import com.example.core_model.Artist
import com.example.infrastructure.mapper.local.toFollowedModels
import com.example.infrastructure.mapper.local.toModels
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteArtistRepositoryImpl @Inject constructor(
    private val localDataSource: FavoriteArtistLocalDataSource,
    private val userManager: UserManager
): FavoriteArtistRepository {
    override fun getFavoriteArtists(): Flow<List<Artist>> {
        val userId = userManager.getCurrentUserId()
        return localDataSource.getFavoriteArtists(userId)
            .map { artists ->
                artists.toFollowedModels()
            }
    }

    override suspend fun addArtistToFavorite(artistName: String) {
        val userId = userManager.getCurrentUserId()
        localDataSource.insert(UserFavoriteArtistCrossRefEntity(userId, artistName))
    }

    override suspend fun removeArtistFromFavorite(artistName: String) {
        val userId = userManager.getCurrentUserId()
        localDataSource.delete(userId, artistName)
    }

    override fun isFavoriteArtist(artistName: String): Flow<Boolean> {
        val userId = userManager.getCurrentUserId()
        return localDataSource.isFavoriteArtist(userId, artistName)
    }
}