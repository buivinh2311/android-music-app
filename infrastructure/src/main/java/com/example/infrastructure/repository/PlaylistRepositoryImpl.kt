package com.example.infrastructure.repository

import com.example.core_database.datasource.playlist.PlaylistLocalDataSource
import com.example.core_domain.repository.PlaylistRepository
import com.example.core_model.Playlist
import com.example.infrastructure.mapper.local.toEntity
import com.example.infrastructure.mapper.local.toModel
import com.example.infrastructure.mapper.local.toModels
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlaylistRepositoryImpl @Inject constructor(
    private val playlistLocalDataSource: PlaylistLocalDataSource
): PlaylistRepository {
    override suspend fun getPlaylistById(playlistId: Int): Playlist? {
        return playlistLocalDataSource.getPlaylistById(playlistId)?.toModel()
    }

    override fun getLimitPlaylists(limit: Int): Flow<List<Playlist>> {
        return playlistLocalDataSource.getLimitPlaylists(limit)
            .map { playlist ->
                playlist.toModels()
            }
    }

    override fun getAllPlaylist(): Flow<List<Playlist>> {
        return playlistLocalDataSource.getAllPlaylists()
            .map { playlist ->
                playlist.toModels()
            }
    }

    override suspend fun insert(playlist: Playlist) {
        playlistLocalDataSource.insert(playlist.toEntity())
    }

    override suspend fun getMaxPlaylistId(): Int? {
        return playlistLocalDataSource.getMaxPlaylistId()
    }

    override suspend fun updateArtwork(playlistId: Int, artwork: String) {
        playlistLocalDataSource.updateArtwork(playlistId, artwork)
    }

    override suspend fun delete(playlistId: Int) {
        playlistLocalDataSource.delete(playlistId)
    }

}