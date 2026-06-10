package com.example.feature_library.domain.usecase

import com.example.core_domain.repository.RecentSongRepository
import com.example.core_model.DisplaySong
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLimitRecentSongsUseCase @Inject constructor(
    private val repository: RecentSongRepository
) {
    operator fun invoke(limit: Int): Flow<List<DisplaySong>> {
        return repository.getLimitRecentSongs(limit)
    }
}