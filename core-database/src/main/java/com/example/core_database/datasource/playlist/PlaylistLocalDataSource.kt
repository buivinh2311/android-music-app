package com.example.core_database.datasource.playlist

import com.example.core_database.entity.playlist.PlaylistEntity

interface PlaylistLocalDataSource {
    suspend fun insert(playlist: PlaylistEntity)
    suspend fun getPlaylistById(playlistId: Int): PlaylistEntity?
    suspend fun getAllPlaylists(): List<PlaylistEntity>
    suspend fun delete(playlistId: Int)
}