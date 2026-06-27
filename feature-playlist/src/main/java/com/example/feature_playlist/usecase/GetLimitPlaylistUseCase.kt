package com.example.feature_playlist.usecase

import com.example.core_domain.repository.PlaylistRepository
import com.example.core_model.Playlist
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLimitPlaylistUseCase @Inject constructor(
    private val repository: PlaylistRepository
) {
    operator fun invoke(limit: Int): Flow<List<Playlist>> {
        return repository.getAllPlaylist()
    }
}