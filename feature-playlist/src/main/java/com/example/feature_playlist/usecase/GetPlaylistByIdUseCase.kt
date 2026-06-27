package com.example.feature_playlist.usecase

import com.example.core_domain.repository.PlaylistRepository
import com.example.core_model.Playlist
import javax.inject.Inject

class GetPlaylistByIdUseCase @Inject constructor(
    private val repository: PlaylistRepository
) {
    suspend operator fun invoke(playlistId: Int): Playlist? {
        return repository.getPlaylistById(playlistId)
    }
}