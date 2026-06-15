package com.example.infrastructure.repository

import com.example.core_database.datasource.user.DownloadSongLocalDataSource
import com.example.core_domain.repository.DownloadSongRepository
import com.example.core_model.Song
import com.example.infrastructure.mapper.local.toModels
import javax.inject.Inject

class DownloadSongRepositoryImpl @Inject constructor(
    private val downloadSongLocalDataSource: DownloadSongLocalDataSource,
): DownloadSongRepository {
    override suspend fun getDownloadSongs(userId: Int): List<Song> {
        return downloadSongLocalDataSource.getDownloadSongs(userId).toModels()
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