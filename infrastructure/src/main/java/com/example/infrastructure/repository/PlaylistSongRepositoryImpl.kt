package com.example.infrastructure.repository

import com.example.core_database.datasource.playlist.PlaylistSongLocalDataSource
import com.example.core_database.entity.playlist.PlaylistSongCrossRefEntity
import com.example.core_domain.repository.PlaylistSongRepository
import com.example.core_model.DisplaySong
import com.example.infrastructure.mapper.local.toDisplayModels
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlaylistSongRepositoryImpl @Inject constructor(
    private val localDataSource: PlaylistSongLocalDataSource
): PlaylistSongRepository {
    override fun getSongsInPlaylist(playlistId: Int): Flow<List<DisplaySong>> {
        return localDataSource.getSongsInPlaylist(playlistId)
            .map { song ->
                song.toDisplayModels()
            }
    }

    override suspend fun addSongToPlaylist(playlistId: Int, songId: String) {
        localDataSource.insert(PlaylistSongCrossRefEntity(playlistId, songId))
    }

    override suspend fun removeSongFromPlaylist(playlistId: Int, songId: String) {
        localDataSource.delete(playlistId, songId)
    }

    override suspend fun isSongInPlaylist(playlistId: Int, songId: String):Boolean {
        return localDataSource.isSongInPlaylist(playlistId, songId)
    }
}