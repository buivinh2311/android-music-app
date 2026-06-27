package com.example.feature_download.usecase

import com.example.core_domain.repository.DownloadSongRepository
import com.example.core_model.download.DownloadInfo
import javax.inject.Inject

class GetDownloadSongInfoUseCase @Inject constructor(
    private val repository: DownloadSongRepository
) {
    suspend operator fun invoke(songId: String): DownloadInfo? {
        return repository.getDownloadInfo(songId)
    }
}