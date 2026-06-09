package com.example.infrastructure.repository

import com.example.core_database.datasource.user.UserFavoriteSongLocalDataSource
import com.example.core_database.entity.user.UserFavoriteSongCrossRefEntity
import com.example.core_domain.manager.UserManager
import com.example.core_domain.repository.FavoriteSongRepository
import com.example.core_model.DisplaySong
import com.example.infrastructure.mapper.local.toDisplayModels
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteSongRepositoryImpl @Inject constructor(
    private val localDataSource: UserFavoriteSongLocalDataSource,
    private val userManager: UserManager
): FavoriteSongRepository {
    override suspend fun getFavoriteSongs(): List<DisplaySong> {
        val userId = userManager.getCurrentUserId()
        return localDataSource.getFavoriteSongs(userId).toDisplayModels()
    }

    override suspend fun addSongToFavorite(songId: String) {
        val userId = userManager.getCurrentUserId()
        localDataSource.insert(UserFavoriteSongCrossRefEntity(userId, songId))
    }

    override suspend fun removeSongFromFavorite(songId: String) {
        val userId = userManager.getCurrentUserId()
        localDataSource.delete(userId, songId)
    }

    override fun isFavoriteSong(songId: String): Flow<Boolean> {
        val userId = userManager.getCurrentUserId()
        return localDataSource.isFavoriteSong(userId, songId)
    }
}