package com.example.feature_album.domain.usecase

import com.example.core_domain.repository.SongRepository
import com.example.core_model.DisplaySong
import javax.inject.Inject

class GetSongsInAlbumUseCase @Inject constructor(
    private val repository: SongRepository
) {
    suspend operator fun invoke(albumName: String): List<DisplaySong> {
        return repository.getSongsByAlbumName(albumName)
    }
}