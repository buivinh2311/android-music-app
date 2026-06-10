package com.example.infrastructure.repository

import com.example.core_database.datasource.user.RecentSongLocalDataSource
import com.example.core_domain.manager.UserManager
import com.example.core_domain.repository.RecentSongRepository
import com.example.core_model.DisplaySong
import com.example.infrastructure.mapper.local.toDisplayModels
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecentSongRepositoryImpl @Inject constructor(
    private val recentSongLocalDataSource: RecentSongLocalDataSource,
    private val userManager: UserManager
): RecentSongRepository {
    override fun getLimitRecentSongs(limit: Int): Flow<List<DisplaySong>> {
        val userId = userManager.getCurrentUserId()
        return recentSongLocalDataSource
            .getLimitRecentSongs(userId, limit)
            .map { song->
                song.toDisplayModels()
            }
    }

    override fun getAllRecentSongs(): Flow<List<DisplaySong>> {
        val userId = userManager.getCurrentUserId()
        return recentSongLocalDataSource
            .getAllRecentSongs(userId)
            .map { song->
                song.toDisplayModels()
            }
    }

    override suspend fun insert(songId: String) {
        val userId = userManager.getCurrentUserId()
        recentSongLocalDataSource.insert(userId, songId)
    }

    override suspend fun delete(songId: String) {
        val userId = userManager.getCurrentUserId()
        recentSongLocalDataSource.delete(userId, songId)
    }

    override suspend fun clearAll() {
        val userId = userManager.getCurrentUserId()
        recentSongLocalDataSource.clearAll(userId)
    }

}