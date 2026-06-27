package com.example.feature_album.usecase

import com.example.core_domain.repository.FavoriteAlbumRepository
import com.example.core_model.Album
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteAlbumUseCase @Inject constructor(
    private val repository: FavoriteAlbumRepository
) {
    operator fun invoke(): Flow<List<Album>> {
        return repository.getFavoriteAlbums()
    }
}