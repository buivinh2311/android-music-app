package com.example.core_domain.repository

import com.example.core_model.Playlist

interface PlaylistRepository {
    suspend fun getPlaylistById(playlistId: Int): Playlist?
    suspend fun getAllPlaylist(): List<Playlist>
    suspend fun insert(playlist: Playlist)
    suspend fun delete(playlistId: Int)
}