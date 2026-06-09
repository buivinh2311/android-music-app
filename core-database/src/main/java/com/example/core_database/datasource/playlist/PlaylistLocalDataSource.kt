package com.example.core_database.datasource.playlist

import com.example.core_database.entity.playlist.PlaylistEntity
import kotlinx.coroutines.flow.Flow

interface PlaylistLocalDataSource {
    suspend fun insert(playlist: PlaylistEntity)
    suspend fun getPlaylistById(playlistId: Int): PlaylistEntity?
    fun getAllPlaylists(): Flow<List<PlaylistEntity>>
    suspend fun getMaxPlaylistId(): Int?
    suspend fun updateSize(playlistId: Int, size: Int)
    suspend fun delete(playlistId: Int)
}