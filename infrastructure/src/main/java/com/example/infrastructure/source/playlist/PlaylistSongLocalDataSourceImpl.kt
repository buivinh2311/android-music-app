package com.example.infrastructure.source.playlist

import com.example.core_database.dao.playlist.PlaylistSongCrossRefDao
import com.example.core_database.datasource.playlist.PlaylistSongLocalDataSource
import com.example.core_database.entity.playlist.PlaylistSongCrossRefEntity
import javax.inject.Inject

class PlaylistSongLocalDataSourceImpl @Inject constructor(
    private val playlistSongCrossRefDao: PlaylistSongCrossRefDao
): PlaylistSongLocalDataSource {
    override suspend fun insertAll(crossRefs: List<PlaylistSongCrossRefEntity>) {
        playlistSongCrossRefDao.insertAll(crossRefs)
    }

    override suspend fun getCrossRefByPlaylistId(playlistId: Int): PlaylistSongCrossRefEntity? {
        return playlistSongCrossRefDao.getCrossRefByPlaylistId(playlistId)
    }

    override suspend fun clearAll() {
        return playlistSongCrossRefDao.clearAll()
    }
}