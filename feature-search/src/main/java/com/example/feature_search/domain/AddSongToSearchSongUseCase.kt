package com.example.feature_search.domain

import com.example.core_domain.repository.SearchSongRepository
import javax.inject.Inject

class AddSongToSearchSongUseCase @Inject constructor(
    private val repository: SearchSongRepository
) {
    suspend operator fun invoke(songId: String) {
        repository.insert(songId)
    }
}