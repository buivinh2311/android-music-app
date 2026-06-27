package com.example.feature_artist.usecase

import com.example.core_domain.repository.FavoriteArtistRepository
import com.example.core_model.Artist
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteArtistUseCase @Inject constructor(
    private val repository: FavoriteArtistRepository
) {
    operator fun invoke(): Flow<List<Artist>> {
        return repository.getFavoriteArtists()
    }
}