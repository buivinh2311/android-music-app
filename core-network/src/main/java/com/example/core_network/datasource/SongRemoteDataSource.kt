package com.example.core_network.datasource

import com.example.core_network.dto.PagingParamRequest
import com.example.core_network.dto.SongDto
import com.example.core_network.dto.SongListDto

interface SongRemoteDataSource {
    suspend fun loadSongPaging(param: PagingParamRequest): SongListDto
    suspend fun updateCounter(songId: String)
    suspend fun getFirstNSongs(playlistId: Int, limit: Int): SongListDto
    suspend fun getPreviousNSongs(playlistId: Int, songId: String, limit: Int): SongListDto
    suspend fun getNextNSongs(playlistId: Int, songId: String, limit: Int): SongListDto
    suspend fun getLastNSongs(playlistId: Int, limit: Int): SongListDto
    suspend fun getAllSong(): SongListDto
    suspend fun getSongsByAlbumName(albumName: String): SongListDto
    suspend fun getSongsByAlbumId(albumId: Int): SongListDto
    suspend fun findSongsBySongNameOrArtistName(query: String): SongListDto
    suspend fun getSongsByArtistName(artistName: String): List<SongDto>
    suspend fun getSongsByArtistId(artistId: Int): SongListDto
    suspend fun getSongById(songId: String): SongDto?
    suspend fun getRecommendedSongs(limit: Int): List<SongDto>
    suspend fun getMostHeardSongs(limit: Int): List<SongDto>
    suspend fun getForYouSongs(limit: Int): List<SongDto>
}