package com.example.feature_album.usecase

import com.example.core_domain.repository.AlbumRepository
import com.example.core_model.Album
import javax.inject.Inject

class GetAlbumDetailUseCase @Inject constructor(
    private val repository: AlbumRepository
) {
    suspend operator fun invoke(albumName: String): Album? {
        return repository.getAlbumByName(albumName)
    }
}