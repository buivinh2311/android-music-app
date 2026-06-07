package com.example.feature_player.domain.usecase

import com.example.core_domain.repository.SongRepository
import com.example.core_model.DisplaySong
import javax.inject.Inject

class GetDisplaySongByIdUseCase @Inject constructor(
    private val repository: SongRepository
) {
    suspend operator fun invoke(songId: String): DisplaySong? {
        return repository.getDisplaySongById(songId)
    }
}