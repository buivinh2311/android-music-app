package com.example.feature_album.domain.usecase

import com.example.core_domain.repository.SongRepository
import com.example.core_model.Song
import javax.inject.Inject

class GetSongsInAlbumUseCase @Inject constructor(
    private val repository: SongRepository
) {
    suspend operator fun invoke(albumName: String): List<Song> {
        return repository.getSongsByAlbumName(albumName)
    }
}