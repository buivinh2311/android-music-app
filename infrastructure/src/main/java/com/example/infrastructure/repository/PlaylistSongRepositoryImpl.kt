package com.example.infrastructure.repository

import com.example.core_database.datasource.playlist.PlaylistLocalDataSource
import com.example.core_database.datasource.playlist.PlaylistSongLocalDataSource
import com.example.core_database.entity.playlist.PlaylistSongCrossRefEntity
import com.example.core_domain.repository.PlaylistSongRepository
import com.example.core_model.Song
import com.example.infrastructure.mapper.local.toModels
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlaylistSongRepositoryImpl @Inject constructor(
    private val playlistSongLocalDataSource: PlaylistSongLocalDataSource,
    private val playlistLocalDataSource: PlaylistLocalDataSource
): PlaylistSongRepository {
    override fun getSongsInPlaylist(playlistId: Int): Flow<List<Song>> {
        return playlistSongLocalDataSource.getSongsInPlaylist(playlistId)
            .map { song ->
                song.toModels()
            }
    }

    override suspend fun addSongToPlaylist(playlistId: Int, songId: String) {
        if(!isSongInPlaylist(playlistId, songId)) {
            playlistSongLocalDataSource
                .insert(PlaylistSongCrossRefEntity(playlistId, songId))
            val artwork = getSongsInPlaylist(playlistId).first().firstOrNull()?.artworkUrl
            artwork?.let {
                playlistLocalDataSource.updateArtwork(playlistId, artwork)
            }
        }
    }

    override suspend fun removeSongFromPlaylist(playlistId: Int, songId: String) {
        playlistSongLocalDataSource.delete(playlistId, songId)
        val artwork = getSongsInPlaylist(playlistId).first().firstOrNull()?.artworkUrl
        playlistLocalDataSource.updateArtwork(playlistId, artwork ?: "")
    }

    override suspend fun removeAllSongFromPlaylist(playlistId: Int) {
        playlistSongLocalDataSource.deleteByPlaylist(playlistId)
    }

    override suspend fun isSongInPlaylist(playlistId: Int, songId: String):Boolean {
        return playlistSongLocalDataSource.isSongInPlaylist(playlistId, songId)
    }
}