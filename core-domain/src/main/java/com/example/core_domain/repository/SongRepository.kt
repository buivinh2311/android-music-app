package com.example.core_domain.repository

import com.example.core_model.DisplaySong
import com.example.core_model.Song
import com.example.core_network.dto.PagingParamRequest

interface SongRepository {
    suspend fun loadSongPaging(param: PagingParamRequest): List<DisplaySong>
    suspend fun updateCounter(songId: String)
    suspend fun getFirstNSongs(playlistId: Int, limit: Int): List<DisplaySong>
    suspend fun getSongsByAlbumId(albumId: Int): List<DisplaySong>
    suspend fun getSongsByAlbumName(albumName: String): List<DisplaySong>
    suspend fun getSongsByArtistId(artistId: Int): List<DisplaySong>
    suspend fun getSongsByArtistName(artistName: String): List<DisplaySong>
    suspend fun getDisplaySongById(songId: String): DisplaySong?
    suspend fun getSongByPlaylistId(playlistId: Int): List<DisplaySong>
    suspend fun getSongById(songId: String): Song?
    suspend fun getRecommendedSongs(limit: Int):List<DisplaySong>
    suspend fun getMostHeardSongs(limit: Int): List<DisplaySong>
    suspend fun getForYouSongs(limit: Int): List<DisplaySong>
}