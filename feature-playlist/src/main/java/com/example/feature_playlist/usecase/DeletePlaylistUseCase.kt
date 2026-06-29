package com.example.feature_playlist.usecase

import com.example.core_domain.repository.PlaylistRepository
import javax.inject.Inject

class DeletePlaylistUseCase @Inject constructor(
    private val repository: PlaylistRepository
) {
    suspend operator fun invoke(playlistId: Int) {
        repository.delete(playlistId)
    }
}