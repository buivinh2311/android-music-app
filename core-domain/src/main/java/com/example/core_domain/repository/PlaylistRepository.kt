package com.example.core_domain.repository

import com.example.core_model.Playlist
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {
    fun getPlaylistById(playlistId: Int): Flow<Playlist?>
    fun getLimitPlaylists(limit: Int): Flow<List<Playlist>>
    fun getAllPlaylist(): Flow<List<Playlist>>
    suspend fun insert(playlist: Playlist)
    suspend fun getMaxPlaylistId(): Int?
    suspend fun updateArtwork(playlistId: Int, artwork: String)
    suspend fun rename(playlistId: Int, newName: String)
    suspend fun delete(playlistId: Int)
}