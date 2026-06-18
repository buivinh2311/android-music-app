package com.example.feature_search.domain

import com.example.core_domain.repository.SearchSongRepository
import javax.inject.Inject

class ClearAllSearchSongsUseCase @Inject constructor(
    private val repository: SearchSongRepository
) {
    suspend operator fun invoke() {
        repository.clearAll()
    }
}