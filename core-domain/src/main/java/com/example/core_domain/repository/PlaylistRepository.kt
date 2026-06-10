package com.example.core_domain.repository

import com.example.core_model.Playlist
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {
    suspend fun getPlaylistById(playlistId: Int): Playlist?
    fun getLimitPlaylists(limit: Int): Flow<List<Playlist>>
    fun getAllPlaylist(): Flow<List<Playlist>>
    suspend fun insert(playlist: Playlist)
    suspend fun getMaxPlaylistId(): Int?
    suspend fun updateArtwork(playlistId: Int, artwork: String)
    suspend fun delete(playlistId: Int)
}