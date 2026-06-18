package com.example.infrastructure.repository

import com.example.core_database.datasource.user.SearchSongLocalDataSource
import com.example.core_database.entity.user.UserSearchSongCrossRefEntity
import com.example.core_domain.manager.UserManager
import com.example.core_domain.repository.SearchSongRepository
import com.example.core_model.Song
import com.example.infrastructure.mapper.local.toModels
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchSongRepositoryImpl @Inject constructor(
    private val localDataSource: SearchSongLocalDataSource,
    private val userManager: UserManager
): SearchSongRepository {
    override fun getSearchSongs(): Flow<List<Song>> {
        val userId = userManager.getCurrentUserId()
        return localDataSource.getSearchedSongs(userId)
            .map { songs ->
                songs.toModels()
            }
    }

    override suspend fun insert(songId: String) {
        val userId = userManager.getCurrentUserId()
        localDataSource.insert(UserSearchSongCrossRefEntity(userId, songId))
    }

    override suspend fun delete(songId: String) {
        val userId = userManager.getCurrentUserId()
        localDataSource.delete(UserSearchSongCrossRefEntity(userId, songId))
    }

    override suspend fun clearAll() {
        val userId = userManager.getCurrentUserId()
        localDataSource.clearAll(userId)
    }
}