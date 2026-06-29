package com.example.infrastructure.source.playlist

import com.example.core_database.dao.playlist.PlaylistSongCrossRefDao
import com.example.core_database.datasource.playlist.PlaylistSongLocalDataSource
import com.example.core_database.entity.playlist.PlaylistSongCrossRefEntity
import com.example.core_database.entity.song.SongEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlaylistSongLocalDataSourceImpl @Inject constructor(
    private val playlistSongCrossRefDao: PlaylistSongCrossRefDao
): PlaylistSongLocalDataSource {
    override suspend fun insert(crossRef: PlaylistSongCrossRefEntity) {
        playlistSongCrossRefDao.insert(crossRef)
    }

    override suspend fun getCrossRefByPlaylistId(playlistId: Int): List<PlaylistSongCrossRefEntity> {
        return playlistSongCrossRefDao.getCrossRefByPlaylistId(playlistId)
    }

    override suspend fun isSongInPlaylist(playlistId: Int, songId: String): Boolean {
        return playlistSongCrossRefDao.isSongInPlaylist(playlistId, songId)
    }

    override fun getSongsInPlaylist(playlistId: Int): Flow<List<SongEntity>> {
        return playlistSongCrossRefDao.getSongsInPlaylist(playlistId)
    }

    override suspend fun getAllSongIds(): List<String> {
        return playlistSongCrossRefDao.getAllSongIds()
    }

    override suspend fun delete(playlistId: Int, songId: String) {
        playlistSongCrossRefDao.delete(playlistId, songId)
    }

    override suspend fun deleteByPlaylist(playlistId: Int) {
        playlistSongCrossRefDao.deleteByPlaylist(playlistId)
    }

    override suspend fun clearAll() {
        return playlistSongCrossRefDao.clearAll()
    }
}