package com.example.feature_playlist.domain.usecase

import com.example.core_domain.repository.PlaylistSongRepository
import com.example.core_model.DisplaySong
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSongInPlaylistUseCase @Inject constructor(
    private val repository: PlaylistSongRepository
) {
    suspend operator fun invoke(playlistId: Int): Flow<List<DisplaySong>> {
        return repository.getSongsInPlaylist(playlistId)
    }
}