package com.example.feature_artist.domain.usecase

import com.example.core_domain.repository.FavoriteArtistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveFavoriteArtistUseCase @Inject constructor(
    private val repository: FavoriteArtistRepository
) {
    operator fun invoke(artistName: String): Flow<Boolean> {
        return repository.isFavoriteArtist(artistName)
    }
}