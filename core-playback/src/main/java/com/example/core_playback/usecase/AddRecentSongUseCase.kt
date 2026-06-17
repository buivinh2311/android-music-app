package com.example.core_playback.usecase

import com.example.core_domain.repository.RecentSongRepository
import javax.inject.Inject

class AddRecentSongUseCase @Inject constructor(
    private val repository: RecentSongRepository
) {
    suspend operator fun invoke(songId: String) {
        repository.insert(songId)
    }
}