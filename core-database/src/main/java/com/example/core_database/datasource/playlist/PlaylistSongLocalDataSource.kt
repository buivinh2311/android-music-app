package com.example.core_database.datasource.playlist

import com.example.core_database.entity.playlist.PlaylistSongCrossRefEntity

interface PlaylistSongLocalDataSource {
    suspend fun insertAll(crossRefs: List<PlaylistSongCrossRefEntity>)
    suspend fun getCrossRefByPlaylistId(playlistId: Int): PlaylistSongCrossRefEntity?
    suspend fun clearAll()
}