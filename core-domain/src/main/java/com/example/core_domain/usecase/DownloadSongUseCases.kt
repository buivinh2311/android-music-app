package com.example.core_domain.usecase

import com.example.core_domain.repository.DownloadSongRepository
import com.example.core_model.Song
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DownloadSongUseCases @Inject constructor(
    private val repository: DownloadSongRepository
) {
    suspend fun addSongToDownload(song: Song) {
        repository.addSongToDownload(
            song.id,
            song.title,
            song.sourceUrl
        )
    }

    fun observeDownloadSong(songId: String): Flow<Boolean> {
        return repository.isDownloadSong(songId)
    }

    suspend fun removeSongFromDownload(songId: String) {
        val downloadId = repository.getDownloadId(songId)
        downloadId?.let {
            repository.remove(downloadId)
        }
        repository.removeSongFromDownload(songId)
    }
}