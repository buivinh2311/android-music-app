package com.example.feature_album.domain.usecase

import com.example.core_domain.repository.FavoriteAlbumRepository
import javax.inject.Inject

class AddAlbumToFavoriteUseCase @Inject constructor(
    private val repository: FavoriteAlbumRepository
) {
    suspend operator fun invoke(albumName: String) {
        repository.addAlbumToFavorite(albumName)
    }
}