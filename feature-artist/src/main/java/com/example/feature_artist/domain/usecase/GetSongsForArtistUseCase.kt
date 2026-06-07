package com.example.feature_artist.domain.usecase

import com.example.core_domain.repository.SongRepository
import com.example.core_model.DisplaySong
import javax.inject.Inject

class GetSongsForArtistUseCase @Inject constructor(
    private val repository: SongRepository
) {
    suspend operator fun invoke(artistName: String): List<DisplaySong> {
        return repository.getSongsByArtistName(artistName)
    }
}