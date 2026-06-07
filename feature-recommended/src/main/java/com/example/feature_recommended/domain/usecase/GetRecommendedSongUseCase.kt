package com.example.feature_recommended.domain.usecase

import com.example.core_domain.repository.SongRepository
import com.example.core_model.DisplaySong
import javax.inject.Inject

class GetRecommendedSongUseCase @Inject constructor(
    private val repository: SongRepository
) {
    suspend operator fun invoke(limit: Int): List<DisplaySong> {
        return repository.getRecommendedSongs(limit)
    }
}