package com.example.infrastructure.source.song

import androidx.paging.PagingSource
import com.example.core_database.dao.song.SongDao
import com.example.core_database.datasource.song.SongLocalDataSource
import com.example.core_database.entity.song.SongEntity
import com.example.core_domain.model.SongListType
import javax.inject.Inject

class SongLocalDataSourceImpl @Inject constructor(
    private val songDao: SongDao
): SongLocalDataSource {
    override fun getSongPagingSource(): PagingSource<Int, SongEntity> {
        return songDao.getSongPagingSource()
    }

    override fun getNSongPagingSource(limit: Int): PagingSource<Int, SongEntity> {
        return songDao.getNSongPagingSource(limit)
    }

    override suspend fun getSongsByAlbumId(albumId: Int): List<SongEntity> {
        return songDao.getSongsByAlbumId(albumId)
    }

    override suspend fun getSongsByAlbumName(albumName: String): List<SongEntity> {
        return songDao.getSongsByAlbumName(albumName)
    }

    override suspend fun getSongsByArtistId(artistId: Int): List<SongEntity> {
        return songDao.getSongsByArtistId(artistId)
    }

    override suspend fun getSongsByArtistName(artistName: String): List<SongEntity> {
        return songDao.getSongsByArtistName(artistName)
    }

    override suspend fun getSongsInPlaylist(playlistId: Int): List<SongEntity> {
        return songDao.getSongsInPlaylist(playlistId)
    }

    override suspend fun getSongById(songId: String): SongEntity? {
        return songDao.getSongById(songId)
    }

    override suspend fun getRecommendedSongs(limit: Int): List<SongEntity> {
        return songDao.getRecommendedSongs(limit, SongListType.RECOMMENDED)
    }

    override suspend fun getMostHeardSongs(limit: Int): List<SongEntity> {
        return songDao.getMostHeardSongs(limit, SongListType.MOST_HEARD)
    }

    override suspend fun getForYouSongs(limit: Int): List<SongEntity> {
        return songDao.getForYouSongs(limit, SongListType.FOR_YOU)
    }

    override suspend fun insert(song: SongEntity) {
        songDao.insert(song)
    }

    override suspend fun insertAll(songs: List<SongEntity>) {
        songDao.insertAll(songs)
    }

    override suspend fun updateCounter(songId: String) {
        songDao.updateCounter(songId)
    }

    override suspend fun delete(songId: String) {
        songDao.delete(songId)
    }

    override suspend fun clearAll() {
        songDao.clearAll()
    }
}