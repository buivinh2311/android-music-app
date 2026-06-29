package com.example.feature_playlist.usecase

import com.example.core_domain.repository.PlaylistSongRepository
import javax.inject.Inject

class RemoveAllSongFromPlaylistUseCase @Inject constructor(
    private val repository: PlaylistSongRepository
) {
    suspend operator fun invoke(playlistId: Int) {
        repository.removeAllSongFromPlaylist(playlistId)
    }
}