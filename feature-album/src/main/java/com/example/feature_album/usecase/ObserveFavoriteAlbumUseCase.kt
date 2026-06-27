package com.example.feature_album.usecase

import com.example.core_domain.repository.FavoriteAlbumRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveFavoriteAlbumUseCase @Inject constructor(
    private val repository: FavoriteAlbumRepository
) {
    operator fun invoke(albumName: String): Flow<Boolean> {
        return repository.isFavoriteAlbum(albumName)
    }
}