package com.example.feature_library.domain.usecase

import com.example.core_domain.repository.PlaylistRepository
import com.example.core_model.Playlist
import dagger.Binds
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLimitPlaylistUseCase @Inject constructor(
    private val repository: PlaylistRepository
) {
    operator fun invoke(limit: Int): Flow<List<Playlist>> {
        return repository.getLimitPlaylists(limit)
    }
}