package com.example.feature_library.domain.usecase

import com.example.core_domain.repository.FavoriteAlbumRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteAlbumCountUseCase @Inject constructor(
    private val repository: FavoriteAlbumRepository
) {
    operator fun invoke(): Flow<Int> {
        return repository.getFavoriteAlbumCount()
    }
}