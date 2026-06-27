package com.example.feature_download.usecase

import com.example.core_domain.repository.DownloadSongRepository
import javax.inject.Inject

class RemoveSongFromDownloadUseCase @Inject constructor(
    private val repository: DownloadSongRepository
) {
    suspend operator fun invoke(songId: String) {
        val downloadId = repository.getDownloadId(songId)
        downloadId?.let {
            repository.remove(downloadId)
        }
        repository.removeSongFromDownload(songId)
    }
}