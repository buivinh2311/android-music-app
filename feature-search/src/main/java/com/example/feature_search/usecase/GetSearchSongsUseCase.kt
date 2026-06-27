package com.example.feature_search.usecase

import com.example.core_domain.repository.SearchSongRepository
import com.example.core_model.Song
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchSongsUseCase @Inject constructor(
    private val repository: SearchSongRepository
) {
    operator fun invoke(): Flow<List<Song>> {
        return repository.getSearchSongs()
    }
}