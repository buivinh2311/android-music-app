package com.example.feature_download.usecase

import com.example.core_domain.repository.DownloadSongRepository
import com.example.core_model.Song
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllDownloadSongsUseCase @Inject constructor(
    private val repository: DownloadSongRepository
) {
    operator fun invoke(): Flow<List<Song>> {
        return repository.getDownloadSongs()
    }
}