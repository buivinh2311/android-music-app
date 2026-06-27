package com.example.feature_playlist.usecase

import com.example.core_domain.repository.PlaylistSongRepository
import com.example.core_model.Song
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSongInPlaylistUseCase @Inject constructor(
    private val repository: PlaylistSongRepository
) {
    operator fun invoke(playlistId: Int): Flow<List<Song>> {
        return repository.getSongsInPlaylist(playlistId)
    }
}