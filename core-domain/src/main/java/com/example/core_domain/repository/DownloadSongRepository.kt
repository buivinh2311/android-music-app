package com.example.core_domain.repository

import com.example.core_model.Song


interface DownloadSongRepository {
    suspend fun getDownloadSongs(userId: Int): List<Song>
    suspend fun addSongToDownload(userId: Int, songId: String)
    suspend fun removeSongFromDownload(userId: Int, songId: String)
    suspend fun isDownloadSong(userId: Int, songId: String): Boolean
}