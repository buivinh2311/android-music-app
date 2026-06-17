package com.example.feature_library.domain.usecase

import com.example.core_domain.repository.DownloadSongRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDownloadSongCountUseCase @Inject constructor(
    private val repository: DownloadSongRepository
) {
    operator fun invoke(): Flow<Int> {
        return repository.getDownloadSongCount()
    }
}