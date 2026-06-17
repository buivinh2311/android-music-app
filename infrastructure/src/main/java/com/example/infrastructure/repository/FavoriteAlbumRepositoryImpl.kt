package com.example.infrastructure.repository

import com.example.core_database.dao.user.UserFavoriteAlbumCrossRefDao
import com.example.core_database.datasource.user.FavoriteAlbumLocalDataSource
import com.example.core_database.entity.user.UserFavoriteAlbumCrossRefEntity
import com.example.core_domain.manager.UserManager
import com.example.core_domain.repository.FavoriteAlbumRepository
import com.example.core_model.Album
import com.example.infrastructure.mapper.local.toModels
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteAlbumRepositoryImpl @Inject constructor(
    private val localDataSource: FavoriteAlbumLocalDataSource,
    private val userManager: UserManager
): FavoriteAlbumRepository {
    override fun getFavoriteAlbums(): Flow<List<Album>> {
        val userId = userManager.getCurrentUserId()
        return localDataSource.getFavoriteAlbums(userId)
            .map { albums ->
                albums.toModels()
            }
    }

    override suspend fun addAlbumToFavorite(albumName: String) {
        val userId = userManager.getCurrentUserId()
        localDataSource.insert(UserFavoriteAlbumCrossRefEntity(userId, albumName))
    }

    override suspend fun removeAlbumFromFavorite(albumName: String) {
        val userId = userManager.getCurrentUserId()
        localDataSource.delete(userId, albumName)
    }

    override fun isFavoriteAlbum(albumName: String): Flow<Boolean> {
        val userId = userManager.getCurrentUserId()
        return localDataSource.isFavoriteAlbum(userId, albumName)
    }

    override fun getFavoriteAlbumCount(): Flow<Int> {
        val userId = userManager.getCurrentUserId()
        return localDataSource.getFavoriteAlbumCount(userId)
    }

}