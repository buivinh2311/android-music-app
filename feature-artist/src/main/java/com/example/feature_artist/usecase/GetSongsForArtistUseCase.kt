package com.example.feature_artist.usecase

import com.example.core_domain.repository.SongRepository
import com.example.core_model.Song
import javax.inject.Inject

class GetSongsForArtistUseCase @Inject constructor(
    private val repository: SongRepository
) {
    suspend operator fun invoke(artistName: String): List<Song> {
        return repository.getSongsByArtistName(artistName)
    }
}