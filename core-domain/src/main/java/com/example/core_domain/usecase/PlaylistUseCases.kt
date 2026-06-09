package com.example.core_domain.usecase

import com.example.core_domain.repository.PlaylistRepository
import com.example.core_domain.repository.PlaylistSongRepository
import com.example.core_model.Playlist
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlaylistUseCases @Inject constructor(
    private val playlistSongRepository: PlaylistSongRepository,
    private val playlistRepository: PlaylistRepository
) {
    fun getAllPlaylist(): Flow<List<Playlist>> {
        return playlistRepository.getAllPlaylist()
    }

    suspend fun addSongToPlaylist(playlistId: Int, songId: String) {
        playlistSongRepository.addSongToPlaylist(playlistId, songId)
        playlistRepository.updateSize(playlistId, 1)
    }

    suspend fun createPlaylist(playlistName: String) {
        val maxPlaylistId = playlistRepository.getMaxPlaylistId() ?: 0
        playlistRepository.insert(Playlist(maxPlaylistId + 1, playlistName))
    }

    suspend fun isSongInPlaylist(playlistId: Int, songId: String): Boolean {
        return playlistSongRepository.isSongInPlaylist(playlistId, songId)
    }

    suspend fun removeSongFromPlaylist(playlistId: Int, songId: String) {
        playlistSongRepository.removeSongFromPlaylist(playlistId, songId)
        playlistRepository.updateSize(playlistId, -1)
    }
}