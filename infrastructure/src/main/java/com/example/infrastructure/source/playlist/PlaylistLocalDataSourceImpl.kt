package com.example.infrastructure.source.playlist

import com.example.core_database.dao.playlist.PlaylistDao
import com.example.core_database.datasource.playlist.PlaylistLocalDataSource
import com.example.core_database.entity.playlist.PlaylistEntity
import com.example.core_database.entity.playlist.PlaylistWithCountEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlaylistLocalDataSourceImpl @Inject constructor(
    private val playlistDao: PlaylistDao
): PlaylistLocalDataSource {
    override suspend fun insert(playlist: PlaylistEntity) {
        playlistDao.insert(playlist)
    }

    override suspend fun getPlaylistById(playlistId: Int): PlaylistWithCountEntity? {
        return playlistDao.getPlaylistById(playlistId)
    }

    override fun getAllPlaylists(): Flow<List<PlaylistWithCountEntity>> {
        return playlistDao.getAllPlaylists()
    }

    override fun getLimitPlaylists(limit: Int): Flow<List<PlaylistWithCountEntity>> {
        return playlistDao.getLimitPlaylists(limit)
    }

    override suspend fun getMaxPlaylistId(): Int? {
        return playlistDao.getMaxPlaylistId()
    }

    override suspend fun updateArtwork(playlistId: Int, artwork: String) {
        playlistDao.updateArtwork(playlistId, artwork)
    }

    override suspend fun delete(playlistId: Int) {
        playlistDao.delete(playlistId)
    }
}