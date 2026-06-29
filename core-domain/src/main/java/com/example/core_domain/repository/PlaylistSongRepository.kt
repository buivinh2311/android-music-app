package com.example.core_domain.repository

import com.example.core_model.Song
import kotlinx.coroutines.flow.Flow

interface PlaylistSongRepository {
    fun getSongsInPlaylist(playlistId: Int): Flow<List<Song>>
    suspend fun addSongToPlaylist(playlistId: Int, songId: String)
    suspend fun removeSongFromPlaylist(playlistId: Int, songId: String)
    suspend fun removeAllSongFromPlaylist(playlistId: Int)
    suspend fun isSongInPlaylist(playlistId: Int, songId: String): Boolean
}