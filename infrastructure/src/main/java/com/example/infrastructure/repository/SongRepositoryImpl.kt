package com.example.infrastructure.repository

import androidx.room.withTransaction
import com.example.core_database.datasource.album.AlbumSongLocalDataSource
import com.example.core_database.datasource.artist.ArtistSongLocalDataSource
import com.example.core_database.datasource.playlist.PlaylistSongLocalDataSource
import com.example.core_database.datasource.song.SongListLocalDataSource
import com.example.core_database.datasource.song.SongLocalDataSource
import com.example.core_database.datasource.tracking.DBTrackingDataSource
import com.example.core_database.datasource.user.DownloadSongLocalDataSource
import com.example.core_database.datasource.user.FavoriteSongLocalDataSource
import com.example.core_database.datasource.user.RecentSongLocalDataSource
import com.example.core_database.datasource.user.SearchSongLocalDataSource
import com.example.core_database.entity.album.AlbumSongCrossRefEntity
import com.example.core_database.entity.artist.ArtistSongCrossRefEntity
import com.example.core_database.entity.song.SongListEntity
import com.example.core_database.entity.tracking.DBTrackingEntity
import com.example.core_domain.manager.UserManager
import com.example.core_model.SongListType
import com.example.core_domain.repository.SongRepository
import com.example.core_model.Song
import com.example.core_network.datasource.SongRemoteDataSource
import com.example.core_network.dto.PagingParamRequest
import com.example.core_utils.util.AppUtil
import com.example.infrastructure.database.AppDatabase
import com.example.infrastructure.mapper.local.toEntities
import com.example.infrastructure.mapper.local.toEntity
import com.example.infrastructure.mapper.local.toModel
import com.example.infrastructure.mapper.local.toModels
import com.example.infrastructure.mapper.remote.toModels
import com.example.infrastructure.mapper.remote.toModel
import javax.inject.Inject

class SongRepositoryImpl @Inject constructor(
    private val songLocalDataSource: SongLocalDataSource,
    private val songRemoteDataSource: SongRemoteDataSource,
    private val songListLocalDataSource: SongListLocalDataSource,
    private val artistSongLocalDataSource: ArtistSongLocalDataSource,
    private val albumSongLocalDataSource: AlbumSongLocalDataSource,
    private val favoriteSongLocalDataSource: FavoriteSongLocalDataSource,
    private val downloadSongLocalDataSource: DownloadSongLocalDataSource,
    private val playlistSongLocalDataSource: PlaylistSongLocalDataSource,
    private val recentSongLocalDataSource: RecentSongLocalDataSource,
    private val searchSongLocalDataSource: SearchSongLocalDataSource,
    private val dbTrackingDataSource: DBTrackingDataSource,
    private val database: AppDatabase,
    private val userManager: UserManager
) : SongRepository {
    private fun isCacheExpired(lastUpdated: Long): Boolean {
        return System.currentTimeMillis() - lastUpdated > AppUtil.CACHE_TIMEOUT
    }

    private suspend fun getValidSongIds(userId: Int): Set<String> {
        return buildSet {
            addAll(
                songListLocalDataSource.getSongIds(SongListType.FOR_YOU)
            )
            addAll(
                songListLocalDataSource.getSongIds(SongListType.MOST_HEARD)
            )
            addAll(
                songListLocalDataSource.getSongIds(SongListType.RECOMMENDED)
            )
            addAll(
                favoriteSongLocalDataSource.getAllFavoriteSongIds(userId)
            )
            addAll(
                playlistSongLocalDataSource.getAllSongIds()
            )
            addAll(
                downloadSongLocalDataSource.getAllDownloadSongIds(userId)
            )
            addAll(
                recentSongLocalDataSource.getAllRecentSongIds(userId)
            )
            addAll(
                searchSongLocalDataSource.getAllSearchSongIds(userId)
            )
        }
    }

    override suspend fun cleanUp() {
        if (dbTrackingDataSource.getTracking() == null) {
            dbTrackingDataSource.insert(
                DBTrackingEntity()
            )
        }
        val tracking = dbTrackingDataSource.getTracking() ?: return
        if (!isCacheExpired(tracking.lastRecommendedSongUpdated,)) {
            return
        }
        database.withTransaction {
            val userId = userManager.getCurrentUserId()
            val validSongIds = getValidSongIds(userId)
            songLocalDataSource.deleteSongNotIn(validSongIds)
            dbTrackingDataSource.updateCleanUpTimestamp(System.currentTimeMillis())
        }
    }

    override suspend fun loadSongPaging(param: PagingParamRequest): List<Song> {
        return try {
            songRemoteDataSource.loadSongPaging(param).songListDto.toModels()
        } catch (_: Exception) {
            emptyList()
        }
    }

    override suspend fun updateCounter(songId: String) {
        return try {
            songRemoteDataSource.updateCounter(songId)
            songLocalDataSource.updateCounter(songId)
        } catch (_: Exception) {

        }
    }

    override suspend fun getFirstNSongs(
        playlistId: Int,
        limit: Int
    ): List<Song> {
        return try {
            songRemoteDataSource.getFirstNSongs(playlistId, limit).songListDto.toModels()
        } catch (_: Exception) {
            emptyList()
        }
    }

    override suspend fun getSongsByAlbumId(albumId: Int): List<Song> {
        val localSongs = songLocalDataSource.getSongsByAlbumId(albumId)
        return try {
            val remoteSongs = songRemoteDataSource.getSongsByAlbumId(albumId).songListDto
            val remoteSongModels = remoteSongs.toModels()
            if (localSongs.isEmpty() || localSongs.size != remoteSongs.size) {
                songLocalDataSource.insertAll(remoteSongModels.toEntities())
                val crossRefs = remoteSongModels.map { song ->
                    AlbumSongCrossRefEntity(albumId, song.id)
                }
                albumSongLocalDataSource.insertAll(crossRefs)
                remoteSongs.toModels()
            } else {
                localSongs.toModels()
            }
        } catch (_: Exception) {
            localSongs.toModels()
        }
    }

    override suspend fun getSongsByAlbumName(albumName: String): List<Song> {
        val localSongs = songLocalDataSource.getSongsByAlbumName(albumName)
        return try {
            val remoteSongs = songRemoteDataSource.getSongsByAlbumName(albumName).songListDto
            val remoteSongModels = remoteSongs.toModels()
            if (localSongs.isEmpty() || localSongs.size != remoteSongs.size) {
                songLocalDataSource.insertAll(remoteSongModels.toEntities())
                remoteSongs.toModels()
            } else {
                localSongs.toModels()
            }
        } catch (_: Exception) {
            localSongs.toModels()
        }
    }

    override suspend fun getSongsByArtistId(artistId: Int): List<Song> {
        val localSongs = songLocalDataSource.getSongsByArtistId(artistId)
        return try {
            val remoteSongs = songRemoteDataSource.getSongsByArtistId(artistId).songListDto
            val remoteSongModels = remoteSongs.toModels()
            if (localSongs.isEmpty() || localSongs.size != remoteSongs.size) {
                songLocalDataSource.insertAll(remoteSongModels.toEntities())
                val crossRefs = remoteSongModels.map { song ->
                    ArtistSongCrossRefEntity(artistId, song.id)
                }
                artistSongLocalDataSource.insertAll(crossRefs)
                remoteSongs.toModels()
            } else {
                localSongs.toModels()
            }
        } catch (_: Exception) {
            localSongs.toModels()
        }
    }

    override suspend fun getSongsByArtistName(artistName: String): List<Song> {
        val localSongs = songLocalDataSource.getSongsByArtistName(artistName)
        return try {
            val remoteSongs = songRemoteDataSource.getSongsByArtistName(artistName)
            val remoteSongModels = remoteSongs.toModels()
            if (localSongs.isEmpty() || localSongs.size != remoteSongs.size) {
                songLocalDataSource.insertAll(remoteSongModels.toEntities())
                remoteSongs.toModels()
            } else {
                localSongs.toModels()
            }
        } catch (_: Exception) {
            localSongs.toModels()
        }
    }

    override suspend fun findSongsBySongNameOrArtistName(query: String): List<Song> {
        return try {
            songRemoteDataSource.findSongsBySongNameOrArtistName(query).songListDto.toModels()
        } catch (_: Exception) {
            emptyList()
        }
    }

    override suspend fun getSongByPlaylistId(playlistId: Int): List<Song> {
        return songLocalDataSource.getSongsInPlaylist(playlistId).toModels()
    }

    override suspend fun getSongById(songId: String): Song? {
        val localSong = songLocalDataSource.getSongById(songId)
        return localSong?.toModel() ?: try {
            songRemoteDataSource.getSongById(songId)?.toModel()
        } catch (_: Exception) {
            null
        }
    }

    override suspend fun getRecommendedSongs(limit: Int): List<Song> {
        val localSongs = songLocalDataSource.getRecommendedSongs(limit)
        val tracking = dbTrackingDataSource.getTracking() ?: return localSongs.toModels()
        return if (!isCacheExpired(tracking.lastRecommendedSongUpdated)
            && localSongs.size >= limit) {
            localSongs.toModels()
        } else {
            try {
                val remoteSongs = songRemoteDataSource.getRecommendedSongs(limit)
                val remoteSongModels = remoteSongs.toModels()
                database.withTransaction {
                    songLocalDataSource.insertAll(remoteSongModels.toEntities())
                    val songLists = remoteSongModels.map { song ->
                        SongListEntity(song.id, SongListType.RECOMMENDED)
                    }
                    songListLocalDataSource.deleteByType(SongListType.RECOMMENDED)
                    songListLocalDataSource.insertAll(songLists)
                }
                dbTrackingDataSource.updateRecommendedSongTimestamp(System.currentTimeMillis())
                remoteSongs.toModels()
            } catch (_: Exception) {
                localSongs.toModels()
            }
        }
    }

    override suspend fun getMostHeardSongs(limit: Int): List<Song> {
        val localSongs = songLocalDataSource.getMostHeardSongs(limit)
        val tracking = dbTrackingDataSource.getTracking() ?: return localSongs.toModels()
        return if (!isCacheExpired(tracking.lastMostHeardSongUpdated)
            && localSongs.size >= limit) {
            localSongs.toModels()
        } else {
            try {
                val remoteSongs = songRemoteDataSource.getMostHeardSongs(limit)
                val remoteSongModels = remoteSongs.toModels()
                database.withTransaction {
                    songLocalDataSource.insertAll(remoteSongModels.toEntities())
                    val songLists = remoteSongModels.map { song ->
                        SongListEntity(song.id, SongListType.MOST_HEARD)
                    }
                    songListLocalDataSource.deleteByType(SongListType.MOST_HEARD)
                    songListLocalDataSource.insertAll(songLists)
                }
                dbTrackingDataSource.updateMostHeardTimestamp(System.currentTimeMillis())
                remoteSongs.toModels()
            } catch (_: Exception) {
                localSongs.toModels()
            }
        }
    }

    override suspend fun getForYouSongs(limit: Int): List<Song> {
        val localSongs = songLocalDataSource.getForYouSongs(limit)
        val tracking = dbTrackingDataSource.getTracking() ?: return localSongs.toModels()
        return if (!isCacheExpired(tracking.lastForYouSongUpdated)
            && localSongs.size >= limit) {
            localSongs.toModels()
        } else {
            try {
                val remoteSongs = songRemoteDataSource.getForYouSongs(limit)
                val remoteSongModels = remoteSongs.toModels()
                database.withTransaction {
                    songLocalDataSource.insertAll(remoteSongModels.toEntities())
                    val songLists = remoteSongModels.map { song ->
                        SongListEntity(song.id, SongListType.FOR_YOU)
                    }
                    songListLocalDataSource.deleteByType(SongListType.FOR_YOU)
                    songListLocalDataSource.insertAll(songLists)
                }
                dbTrackingDataSource.updateForYouSongTimestamp(System.currentTimeMillis())
                remoteSongs.toModels()
            } catch (_: Exception) {
                localSongs.toModels()
            }
        }
    }

    override suspend fun insert(song: Song) {
        songLocalDataSource.insert(song.toEntity())
    }

}