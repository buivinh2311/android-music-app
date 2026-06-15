package com.example.feature_home.domain.usecase

import com.example.core_domain.repository.SongRepository
import com.example.core_model.Song
import javax.inject.Inject

class GetRecommendedSongsUseCase @Inject constructor(
    private val repository: SongRepository
) {
    suspend operator fun invoke(limit: Int): List<Song> {
        return repository.getRecommendedSongs(limit)
    }
}