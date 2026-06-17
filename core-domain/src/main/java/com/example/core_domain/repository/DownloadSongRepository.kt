package com.example.core_domain.repository

import com.example.core_model.Song
import kotlinx.coroutines.flow.Flow


interface DownloadSongRepository {
    fun getDownloadSongs(): Flow<List<Song>>
    suspend fun addSongToDownload(songId: String)
    suspend fun removeSongFromDownload(songId: String)
    fun isDownloadSong(songId: String): Flow<Boolean>
    fun getDownloadSongCount(): Flow<Int>
}