package com.example.feature_discovery.usecase

import com.example.core_domain.repository.ArtistRepository
import com.example.core_model.Artist
import javax.inject.Inject

class GetTopArtistUseCase @Inject constructor(
    private val repository: ArtistRepository
) {
    suspend operator fun invoke(limit: Int): List<Artist> {
        return repository.getTopArtists(limit)
    }
}