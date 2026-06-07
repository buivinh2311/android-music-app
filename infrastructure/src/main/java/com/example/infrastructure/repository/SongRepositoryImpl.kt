package com.example.infrastructure.repository

import android.util.Log
import com.example.core_database.datasource.album.AlbumSongLocalDataSource
import com.example.core_database.datasource.artist.ArtistSongLocalDataSource
import com.example.core_database.datasource.song.SongListLocalDataSource
import com.example.core_database.datasource.song.SongLocalDataSource
import com.example.core_database.entity.album.AlbumSongCrossRefEntity
import com.example.core_database.entity.artist.ArtistSongCrossRefEntity
import com.example.core_database.entity.song.SongListEntity
import com.example.core_domain.model.SongListType
import com.example.core_domain.repository.SongRepository
import com.example.core_model.DisplaySong
import com.example.core_model.Song
import com.example.core_network.datasource.SongRemoteDataSource
import com.example.core_network.dto.PagingParamRequest
import com.example.infrastructure.mapper.local.toDisplayModels
import com.example.infrastructure.mapper.local.toEntities
import com.example.infrastructure.mapper.local.toModel
import com.example.infrastructure.mapper.remote.toDisplayModel
import com.example.infrastructure.mapper.remote.toDisplayModels
import com.example.infrastructure.mapper.remote.toModel
import com.example.infrastructure.mapper.remote.toModels
import javax.inject.Inject

class SongRepositoryImpl @Inject constructor(
    private val songLocalDataSource: SongLocalDataSource,
    private val songRemoteDataSource: SongRemoteDataSource,
    private val songListLocalDataSource: SongListLocalDataSource,
    private val artistSongLocalDataSource: ArtistSongLocalDataSource,
    private val albumSongLocalDataSource: AlbumSongLocalDataSource
): SongRepository {
    override suspend fun loadSongPaging(param: PagingParamRequest): List<DisplaySong> {
        return try {
            songRemoteDataSource.loadSongPaging(param).songListDto.toDisplayModels()
        } catch (_: Exception) {
            emptyList()
        }
    }

    override suspend fun updateCounter(songId: String) {
        return try {
            songRemoteDataSource.updateCounter(songId)
        } catch (_: Exception) {

        }
    }

    override suspend fun getFirstNSongs(
        playlistId: Int,
        limit: Int
    ): List<DisplaySong> {
        return try {
            songRemoteDataSource.getFirstNSongs(playlistId, limit).songListDto.toDisplayModels()
        } catch (_: Exception) {
            emptyList()
        }
    }

    override suspend fun getSongsByAlbumId(albumId: Int): List<DisplaySong> {
        val localSongs = songLocalDataSource.getSongsByAlbumId(albumId)
        return try {
            val remoteSongs = songRemoteDataSource.getSongsByAlbumId(albumId).songListDto
            val remoteSongModels = remoteSongs.toModels()
            if(localSongs.isEmpty() || localSongs.size != remoteSongs.size) {
                songLocalDataSource.insertAll(remoteSongModels.toEntities())
                val crossRefs = remoteSongModels.map { song ->
                    AlbumSongCrossRefEntity(albumId, song.id)
                }
                albumSongLocalDataSource.insertAll(crossRefs)
                remoteSongs.toDisplayModels()
            } else {
                localSongs.toDisplayModels()
            }
        } catch (_: Exception) {
            localSongs.toDisplayModels()
        }
    }

    override suspend fun getSongsByAlbumName(albumName: String): List<DisplaySong> {
        val localSongs = songLocalDataSource.getSongsByAlbumName(albumName)
        return try {
            val remoteSongs = songRemoteDataSource.getSongsByAlbumName(albumName).songListDto
            val remoteSongModels = remoteSongs.toModels()
            if(localSongs.isEmpty() || localSongs.size != remoteSongs.size) {
                songLocalDataSource.insertAll(remoteSongModels.toEntities())
                remoteSongs.toDisplayModels()
            } else {
                localSongs.toDisplayModels()
            }
        } catch (_: Exception) {
            localSongs.toDisplayModels()
        }
    }

    override suspend fun getSongsByArtistId(artistId: Int): List<DisplaySong> {
        val localSongs = songLocalDataSource.getSongsByArtistId(artistId)
        return try {
            val remoteSongs = songRemoteDataSource.getSongsByArtistId(artistId).songListDto
            val remoteSongModels = remoteSongs.toModels()
            if(localSongs.isEmpty() || localSongs.size != remoteSongs.size) {
                songLocalDataSource.insertAll(remoteSongModels.toEntities())
                val crossRefs = remoteSongModels.map { song ->
                    ArtistSongCrossRefEntity(artistId, song.id)
                }
                artistSongLocalDataSource.insertAll(crossRefs)
                remoteSongs.toDisplayModels()
            } else {
                localSongs.toDisplayModels()
            }
        } catch (_: Exception) {
            localSongs.toDisplayModels()
        }
    }

    override suspend fun getSongsByArtistName(artistName: String): List<DisplaySong> {
        val localSongs = songLocalDataSource.getSongsByArtistName(artistName)
        return try {
            val remoteSongs = songRemoteDataSource.getSongsByArtistName(artistName)
            val remoteSongModels = remoteSongs.toModels()
            if(localSongs.isEmpty() || localSongs.size != remoteSongs.size) {
                songLocalDataSource.insertAll(remoteSongModels.toEntities())
                remoteSongs.toDisplayModels()
            } else {
                localSongs.toDisplayModels()
            }
        } catch (_: Exception) {
            localSongs.toDisplayModels()
        }
    }

    override suspend fun getDisplaySongById(songId: String): DisplaySong? {
        return try {
            songRemoteDataSource.getSongById(songId)?.toDisplayModel()
        } catch (_: Exception) {
            null
        }
    }

    override suspend fun getSongByPlaylistId(playlistId: Int): List<DisplaySong> {
        return songLocalDataSource.getSongsInPlaylist(playlistId).toDisplayModels()
    }

    override suspend fun getSongById(songId: String): Song? {
        val localSong = songLocalDataSource.getSongById(songId)
        return localSong?.toModel() ?: try {
            songRemoteDataSource.getSongById(songId)?.toModel()
        } catch (_: Exception) {
            null
        }
    }

    override suspend fun getRecommendedSongs(limit: Int): List<DisplaySong> {
        val localSongs = songLocalDataSource.getRecommendedSongs(limit)
        return if(localSongs.size >= limit) {
            localSongs.toDisplayModels()
        } else {
            try {
                val remoteSongs = songRemoteDataSource.getRecommendedSongs(limit)
                val remoteSongModels = remoteSongs.toModels()
                songLocalDataSource.insertAll(remoteSongModels.toEntities())
                val songLists = remoteSongModels.map { song ->
                    SongListEntity(song.id, SongListType.RECOMMENDED)
                }
                songListLocalDataSource.deleteByType(SongListType.RECOMMENDED)
                songListLocalDataSource.insertAll(songLists)
                remoteSongs.toDisplayModels()
            } catch (_: Exception) {
                emptyList()
            }
        }
    }

    override suspend fun getMostHeardSongs(limit: Int): List<DisplaySong> {
        val localSongs = songLocalDataSource.getMostHeardSongs(limit)
        return if(localSongs.size >= limit) {
            localSongs.toDisplayModels()
        } else {
            try {
                val remoteSongs = songRemoteDataSource.getMostHeardSongs(limit)
                val remoteSongModels = remoteSongs.toModels()
                songLocalDataSource.insertAll(remoteSongModels.toEntities())
                val songLists = remoteSongModels.map { song ->
                    SongListEntity(song.id, SongListType.MOST_HEARD)
                }
                songListLocalDataSource.deleteByType(SongListType.MOST_HEARD)
                songListLocalDataSource.insertAll(songLists)
                remoteSongs.toDisplayModels()
            } catch (_: Exception) {
                emptyList()
            }
        }
    }

    override suspend fun getForYouSongs(limit: Int): List<DisplaySong> {
        val localSongs = songLocalDataSource.getForYouSongs(limit)
        return if(localSongs.size >= limit) {
            localSongs.toDisplayModels()
        } else {
            try {
                val remoteSongs = songRemoteDataSource.getForYouSongs(limit)
                val remoteSongModels = remoteSongs.toModels()
                songLocalDataSource.insertAll(remoteSongModels.toEntities())
                val songLists = remoteSongModels.map { song ->
                    SongListEntity(song.id, SongListType.FOR_YOU)
                }
                songListLocalDataSource.deleteByType(SongListType.FOR_YOU)
                songListLocalDataSource.insertAll(songLists)
                remoteSongs.toDisplayModels()
            } catch (_: Exception) {
                emptyList()
            }
        }
    }

}