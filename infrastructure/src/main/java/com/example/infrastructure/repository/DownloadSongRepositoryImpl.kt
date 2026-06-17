package com.example.infrastructure.repository

import com.example.core_database.datasource.user.DownloadSongLocalDataSource
import com.example.core_domain.manager.UserManager
import com.example.core_domain.repository.DownloadSongRepository
import com.example.core_model.Song
import com.example.infrastructure.mapper.local.toModels
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DownloadSongRepositoryImpl @Inject constructor(
    private val localDataSource: DownloadSongLocalDataSource,
    private val userManager: UserManager
): DownloadSongRepository {
    override fun getDownloadSongs(): Flow<List<Song>> {
        TODO("Not yet implemented")
    }

    override suspend fun addSongToDownload(songId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun removeSongFromDownload(songId: String) {
        TODO("Not yet implemented")
    }

    override fun isDownloadSong(songId: String): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override fun getDownloadSongCount(): Flow<Int> {
        val userId = userManager.getCurrentUserId()
        return localDataSource.getDownloadSongCount(userId)
    }
}