package com.example.feature_artist.usecase

import com.example.core_domain.repository.ArtistRepository
import com.example.core_model.Artist
import javax.inject.Inject

class GetArtistDetailUseCase @Inject constructor(
    private val repository: ArtistRepository
) {
    suspend operator fun invoke(artistName: String): Artist? {
        return repository.getArtistByName(artistName)
    }
}