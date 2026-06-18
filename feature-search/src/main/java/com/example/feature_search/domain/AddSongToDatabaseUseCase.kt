package com.example.feature_search.domain

import com.example.core_domain.repository.SongRepository
import com.example.core_model.Song
import javax.inject.Inject

class AddSongToDatabaseUseCase @Inject constructor(
    private val repository: SongRepository
) {
    suspend operator fun invoke(song: Song) {
        repository.insert(song)
    }
}