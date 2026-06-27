package com.example.feature_home.usecase

import com.example.core_domain.repository.AlbumRepository
import com.example.core_model.Album
import javax.inject.Inject

class GetTopAlbumUseCase @Inject constructor(
    private val repository: AlbumRepository
) {
    suspend operator fun invoke(limit: Int): List<Album> {
        return repository.getTopAlbums(limit)
    }
}