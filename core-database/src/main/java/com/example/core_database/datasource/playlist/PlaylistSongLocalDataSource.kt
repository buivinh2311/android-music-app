package com.example.core_database.datasource.playlist

import com.example.core_database.entity.playlist.PlaylistSongCrossRefEntity
import com.example.core_database.entity.song.SongEntity
import kotlinx.coroutines.flow.Flow

interface PlaylistSongLocalDataSource {
    suspend fun insert(crossRef: PlaylistSongCrossRefEntity)
    suspend fun getCrossRefByPlaylistId(playlistId: Int): List<PlaylistSongCrossRefEntity>
    suspend fun isSongInPlaylist(playlistId: Int, songId: String): Boolean
    fun getSongsInPlaylist(playlistId: Int): Flow<List<SongEntity>>
    suspend fun getAllSongIds(): List<String>
    suspend fun delete(playlistId: Int, songId: String)
    suspend fun deleteByPlaylist(playlistId: Int)
    suspend fun clearAll()
}