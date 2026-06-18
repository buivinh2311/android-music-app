package com.example.core_domain.repository

import com.example.core_model.Song
import com.example.core_network.dto.PagingParamRequest

interface SongRepository {
    suspend fun loadSongPaging(param: PagingParamRequest): List<Song>
    suspend fun updateCounter(songId: String)
    suspend fun getFirstNSongs(playlistId: Int, limit: Int): List<Song>
    suspend fun getSongsByAlbumId(albumId: Int): List<Song>
    suspend fun getSongsByAlbumName(albumName: String): List<Song>
    suspend fun getSongsByArtistId(artistId: Int): List<Song>
    suspend fun getSongsByArtistName(artistName: String): List<Song>
    suspend fun findSongsBySongNameOrArtistName(query: String): List<Song>
    suspend fun getSongById(songId: String): Song?
    suspend fun getSongByPlaylistId(playlistId: Int): List<Song>
    suspend fun getRecommendedSongs(limit: Int):List<Song>
    suspend fun getMostHeardSongs(limit: Int): List<Song>
    suspend fun getForYouSongs(limit: Int): List<Song>
    suspend fun insert(song: Song)
}