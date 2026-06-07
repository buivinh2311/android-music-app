package com.example.core_database.datasource.song

import androidx.paging.PagingSource
import com.example.core_database.entity.song.SongEntity

interface SongLocalDataSource {
    fun getSongPagingSource(): PagingSource<Int, SongEntity>
    fun getNSongPagingSource(limit: Int): PagingSource<Int, SongEntity>
    suspend fun getSongsByAlbumId(albumId: Int): List<SongEntity>
    suspend fun getSongsByAlbumName(albumName: String): List<SongEntity>
    suspend fun getSongsByArtistId(artistId: Int): List<SongEntity>
    suspend fun getSongsByArtistName(artistName: String): List<SongEntity>
    suspend fun getSongsInPlaylist(playlistId: Int): List<SongEntity>
    suspend fun getSongById(songId: String): SongEntity?
    suspend fun getRecommendedSongs(limit: Int): List<SongEntity>
    suspend fun getMostHeardSongs(limit: Int): List<SongEntity>
    suspend fun getForYouSongs(limit: Int): List<SongEntity>
    suspend fun insert(song: SongEntity)
    suspend fun insertAll(songs: List<SongEntity>)
    suspend fun updateCounter(songId: String)
    suspend fun delete(songId: String)
    suspend fun clearAll()
}