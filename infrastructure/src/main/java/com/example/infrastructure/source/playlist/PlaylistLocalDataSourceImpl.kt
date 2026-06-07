package com.example.infrastructure.source.playlist

import com.example.core_database.dao.playlist.PlaylistDao
import com.example.core_database.datasource.playlist.PlaylistLocalDataSource
import com.example.core_database.entity.playlist.PlaylistEntity
import javax.inject.Inject

class PlaylistLocalDataSourceImpl @Inject constructor(
    private val playlistDao: PlaylistDao
): PlaylistLocalDataSource {
    override suspend fun insert(playlist: PlaylistEntity) {
        playlistDao.insert(playlist)
    }

    override suspend fun getPlaylistById(playlistId: Int): PlaylistEntity? {
        return playlistDao.getPlaylistById(playlistId)
    }

    override suspend fun getAllPlaylists(): List<PlaylistEntity> {
        return playlistDao.getAllPlaylists()
    }

    override suspend fun delete(playlistId: Int) {
        playlistDao.delete(playlistId)
    }

//    override suspend fun getPlaylistWithSongs(playlistId: Int): PlaylistWithSongs {
//        TODO("Not yet implemented")
//    }
}