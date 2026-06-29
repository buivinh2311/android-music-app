package com.example.core_database.datasource.playlist

import com.example.core_database.entity.playlist.PlaylistEntity
import com.example.core_database.entity.playlist.PlaylistWithCountEntity
import kotlinx.coroutines.flow.Flow

interface PlaylistLocalDataSource {
    suspend fun insert(playlist: PlaylistEntity)
    fun getPlaylistById(playlistId: Int): Flow<PlaylistWithCountEntity?>
    fun getAllPlaylists(): Flow<List<PlaylistWithCountEntity>>
    fun getLimitPlaylists(limit: Int): Flow<List<PlaylistWithCountEntity>>
    suspend fun getMaxPlaylistId(): Int?
    suspend fun updateArtwork(playlistId: Int, artwork: String)
    suspend fun rename(playlistId: Int, newName: String)
    suspend fun delete(playlistId: Int)
}