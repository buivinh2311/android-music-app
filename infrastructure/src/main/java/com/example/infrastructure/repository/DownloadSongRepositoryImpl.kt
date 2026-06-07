package com.example.infrastructure.repository

import com.example.core_database.datasource.user.UserDownloadSongLocalDataSource
import com.example.core_domain.repository.DownloadSongRepository
import com.example.core_model.DisplaySong
import com.example.infrastructure.mapper.local.toDisplayModels
import javax.inject.Inject

class DownloadSongRepositoryImpl @Inject constructor(
    private val downloadSongLocalDataSource: UserDownloadSongLocalDataSource,
): DownloadSongRepository {
    override suspend fun getDownloadSongs(userId: Int): List<DisplaySong> {
        return downloadSongLocalDataSource.getDownloadSongs(userId).toDisplayModels()
    }

    override suspend fun addSongToDownload(userId: Int, songId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun removeSongFromDownload(userId: Int, songId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun isDownloadSong(userId: Int, songId: String): Boolean {
        TODO("Not yet implemented")
    }
}