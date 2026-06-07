package com.example.core_domain.usecase

import com.example.core_domain.repository.ArtistRepository
import com.example.core_model.Artist
import dagger.Binds
import javax.inject.Inject

class GetArtistByNameUseCase @Inject constructor(
    private val repository: ArtistRepository
) {
    suspend operator fun invoke(artistName: String): Artist? {
        return repository.getArtistByName(artistName)
    }
}