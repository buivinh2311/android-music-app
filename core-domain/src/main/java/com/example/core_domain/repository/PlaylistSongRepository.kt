package com.example.core_domain.repository

import com.example.core_model.DisplaySong
import kotlinx.coroutines.flow.Flow

interface PlaylistSongRepository {
    fun getSongsInPlaylist(playlistId: Int): Flow<List<DisplaySong>>
    suspend fun addSongToPlaylist(playlistId: Int, songId: String)
    suspend fun removeSongFromPlaylist(playlistId: Int, songId: String)
    suspend fun isSongInPlaylist(playlistId: Int, songId: String): Boolean
}