package com.example.feature_recent.domain.usecase

import com.example.core_domain.repository.RecentSongRepository
import com.example.core_model.Song
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLimitRecentSongsUseCase @Inject constructor(
    private val repository: RecentSongRepository
) {
    operator fun invoke(limit: Int): Flow<List<Song>> {
        return repository.getLimitRecentSongs(limit)
    }
}