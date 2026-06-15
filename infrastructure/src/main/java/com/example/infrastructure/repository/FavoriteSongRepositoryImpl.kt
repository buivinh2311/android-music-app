package com.example.infrastructure.repository

import com.example.core_database.datasource.user.FavoriteSongLocalDataSource
import com.example.core_database.entity.user.UserFavoriteSongCrossRefEntity
import com.example.core_domain.manager.UserManager
import com.example.core_domain.repository.FavoriteSongRepository
import com.example.core_model.Song
import com.example.infrastructure.mapper.local.toModels
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteSongRepositoryImpl @Inject constructor(
    private val localDataSource: FavoriteSongLocalDataSource,
    private val userManager: UserManager
): FavoriteSongRepository {
    override fun getFavoriteSongs(): Flow<List<Song>> {
        val userId = userManager.getCurrentUserId()
        return localDataSource.getFavoriteSongs(userId)
            .map { songs ->
                songs.toModels()
            }
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