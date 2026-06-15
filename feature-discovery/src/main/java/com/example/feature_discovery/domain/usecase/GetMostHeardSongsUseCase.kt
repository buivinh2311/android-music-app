package com.example.feature_discovery.domain.usecase

import com.example.core_domain.repository.SongRepository
import com.example.core_model.Song
import javax.inject.Inject

class GetMostHeardSongsUseCase @Inject constructor(
    private val repository: SongRepository
) {
    suspend operator fun invoke(limit: Int): List<Song> {
        return repository.getMostHeardSongs(limit)
    }
}