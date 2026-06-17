package com.example.core_playback.usecase

import com.example.core_domain.repository.SongRepository
import javax.inject.Inject

class IncreasePlayCountUseCase @Inject constructor(
    private val repository: SongRepository
) {
    suspend operator fun invoke(songId: String) {
        repository.updateCounter(songId)
    }
}