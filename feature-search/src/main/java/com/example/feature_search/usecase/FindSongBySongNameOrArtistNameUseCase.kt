package com.example.feature_search.usecase

import com.example.core_domain.repository.SongRepository
import com.example.core_model.Song
import javax.inject.Inject

class FindSongBySongNameOrArtistNameUseCase @Inject constructor(
    private val repository: SongRepository
) {
    suspend operator fun invoke(query: String): List<Song> {
        return repository.findSongsBySongNameOrArtistName(query)
    }
}