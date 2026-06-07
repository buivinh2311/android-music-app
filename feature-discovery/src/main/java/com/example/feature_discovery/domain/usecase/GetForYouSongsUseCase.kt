package com.example.feature_discovery.domain.usecase

import com.example.core_domain.repository.SongRepository
import com.example.core_model.DisplaySong
import javax.inject.Inject

class GetForYouSongsUseCase @Inject constructor(
    private val repository: SongRepository
) {
    suspend operator fun invoke(limit: Int): List<DisplaySong> {
        return repository.getForYouSongs(limit)
    }
}