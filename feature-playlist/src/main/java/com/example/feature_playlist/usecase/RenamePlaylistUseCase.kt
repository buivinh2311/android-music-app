package com.example.feature_playlist.usecase

import com.example.core_domain.repository.PlaylistRepository
import javax.inject.Inject

class RenamePlaylistUseCase @Inject constructor(
    private val repository: PlaylistRepository
) {
    suspend operator fun invoke(playlistId: Int, newName: String) {
        repository.rename(playlistId, newName)
    }
}