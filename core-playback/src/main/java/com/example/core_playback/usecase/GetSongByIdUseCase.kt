package com.example.core_playback.usecase

import com.example.core_domain.repository.SongRepository
import com.example.core_model.Song
import javax.inject.Inject

class GetSongByIdUseCase @Inject constructor(
    private val repository: SongRepository
) {
    suspend operator fun invoke(songId: String): Song? {
        return repository.getSongById(songId)
    }
}