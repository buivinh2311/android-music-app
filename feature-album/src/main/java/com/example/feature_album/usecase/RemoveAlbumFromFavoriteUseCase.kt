package com.example.feature_album.usecase

import com.example.core_domain.repository.FavoriteAlbumRepository
import javax.inject.Inject

class RemoveAlbumFromFavoriteUseCase @Inject constructor(
    private val repository: FavoriteAlbumRepository
) {
    suspend operator fun invoke(albumName: String) {
        repository.removeAlbumFromFavorite(albumName)
    }
}