package com.example.infrastructure.repository

import com.example.core_database.datasource.user.DownloadManagerLocalDataSource
import com.example.core_database.datasource.user.DownloadSongLocalDataSource
import com.example.core_database.entity.user.UserDownloadSongCrossRefEntity
import com.example.core_domain.manager.UserManager
import com.example.core_domain.repository.DownloadSongRepository
import com.example.core_model.Song
import com.example.core_model.download.DownloadInfo
import com.example.infrastructure.mapper.local.toModels
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DownloadSongRepositoryImpl @Inject constructor(
    private val localDataSource: DownloadSongLocalDataSource,
    private val downloadManager: DownloadManagerLocalDataSource,
    private val userManager: UserManager
): DownloadSongRepository {
    override fun getDownloadSongs(): Flow<List<Song>> {
        val userId = userManager.getCurrentUserId()
        return localDataSource.getDownloadSongs(userId)
            .map { songs ->
                songs.toModels()
            }
    }

    override suspend fun addSongToDownload(songId: String, title: String, sourceUrl: String) {
        val userId = userManager.getCurrentUserId()
        val downloadId = downloadManager.enqueue(
            songId = songId,
            title = title,
            sourceUrl = sourceUrl
        )
        localDataSource.insert(
            UserDownloadSongCrossRefEntity(
                userId = userId,
                songId = songId,
                downloadId = downloadId
            )
        )
    }

    override suspend fun removeSongFromDownload(songId: String) {
        val userId = userManager.getCurrentUserId()
        localDataSource.delete(userId, songId)
    }

    override fun isDownloadSong(songId: String): Flow<Boolean> {
        val userId = userManager.getCurrentUserId()
        return localDataSource.isDownloadSong(userId, songId)
    }

    override fun getDownloadSongCount(): Flow<Int> {
        val userId = userManager.getCurrentUserId()
        return localDataSource.getDownloadSongCount(userId)
    }

    override suspend fun getDownloadInfo(songId: String): DownloadInfo? {
        val userId = userManager.getCurrentUserId()
        val entity = localDataSource.getDownloadInfo(userId, songId) ?: return null
        val info = downloadManager.query(entity.downloadId)
        return info
    }

    override suspend fun getDownloadId(songId: String): Long? {
        val userId = userManager.getCurrentUserId()
        val entity = localDataSource.getDownloadInfo(userId, songId) ?: return null
        return entity.downloadId
    }

    override suspend fun enqueue(
        songId: String,
        title: String,
        sourceUrl: String
    ): Long {
        return downloadManager.enqueue(
            songId = songId,
            title = title,
            sourceUrl = sourceUrl
        )
    }

    override suspend fun remove(downloadId: Long) {
        downloadManager.remove(downloadId)
    }

    override suspend fun query(downloadId: Long): DownloadInfo {
        return downloadManager.query(downloadId)
    }
}