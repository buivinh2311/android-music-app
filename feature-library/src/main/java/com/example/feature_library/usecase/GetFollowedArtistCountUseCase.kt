package com.example.feature_library.usecase

import com.example.core_domain.repository.FavoriteArtistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFollowedArtistCountUseCase @Inject constructor(
    private val repository: FavoriteArtistRepository
) {
    operator fun invoke(): Flow<Int> {
        return repository.getFavoriteArtistCount()
    }
}