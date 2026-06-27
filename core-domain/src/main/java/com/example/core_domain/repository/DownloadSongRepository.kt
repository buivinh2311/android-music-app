package com.example.core_domain.repository

import com.example.core_model.Song
import com.example.core_model.download.DownloadInfo
import com.example.core_model.download.DownloadState
import kotlinx.coroutines.flow.Flow


interface DownloadSongRepository {
    fun getDownloadSongs(): Flow<List<Song>>
    suspend fun addSongToDownload(songId: String, title: String, sourceUrl: String)
    suspend fun removeSongFromDownload(songId: String)
    fun isDownloadSong(songId: String): Flow<Boolean>
    fun getDownloadSongCount(): Flow<Int>
    suspend fun getDownloadInfo(songId: String): DownloadInfo?
    suspend fun getDownloadId(songId: String): Long?
    suspend fun enqueue(songId: String, title: String, sourceUrl: String): Long
    suspend fun remove(downloadId: Long)

    suspend fun query(downloadId: Long): DownloadInfo
}