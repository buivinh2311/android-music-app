package com.example.infrastructure.repository

import com.example.core_database.datasource.playlist.PlaylistLocalDataSource
import com.example.core_domain.repository.PlaylistRepository
import com.example.core_model.Playlist
import com.example.infrastructure.mapper.local.toEntity
import com.example.infrastructure.mapper.local.toModel
import com.example.infrastructure.mapper.local.toModels
import javax.inject.Inject

class PlaylistRepositoryImpl @Inject constructor(
    private val playlistLocalDataSource: PlaylistLocalDataSource
): PlaylistRepository {
    override suspend fun getPlaylistById(playlistId: Int): Playlist? {
        return playlistLocalDataSource.getPlaylistById(playlistId)?.toModel()
    }

    override suspend fun getAllPlaylist(): List<Playlist> {
        return playlistLocalDataSource.getAllPlaylists().toModels()
    }

    override suspend fun insert(playlist: Playlist) {
        playlistLocalDataSource.insert(playlist.toEntity())
    }

    override suspend fun delete(playlistId: Int) {
        playlistLocalDataSource.delete(playlistId)
    }

}