package com.example.feature_artist.usecase

import com.example.core_domain.repository.FavoriteArtistRepository
import javax.inject.Inject

class AddArtistToFavoriteUseCase @Inject constructor(
    private val repository: FavoriteArtistRepository
) {
    suspend operator fun invoke(artistName: String) {
        repository.addArtistToFavorite(artistName)
    }
}