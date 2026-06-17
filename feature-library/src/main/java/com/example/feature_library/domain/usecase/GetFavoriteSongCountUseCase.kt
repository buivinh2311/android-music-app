package com.example.feature_library.domain.usecase

import com.example.core_domain.repository.FavoriteSongRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteSongCountUseCase @Inject constructor(
    private val repository: FavoriteSongRepository
) {
    operator fun invoke(): Flow<Int> {
        return repository.getFavoriteSongCount()
    }
}