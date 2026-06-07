package com.example.feature_album.domain.usecase

import com.example.core_domain.repository.AlbumRepository
import com.example.core_model.Album
import javax.inject.Inject

class GetTopAlbumsUseCase @Inject constructor(
    private val repository: AlbumRepository
) {
    suspend operator fun invoke(limit: Int): List<Album> {
        return repository.getTopAlbums(limit)
    }
}

