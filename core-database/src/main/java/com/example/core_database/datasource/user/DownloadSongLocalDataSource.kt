package com.example.core_database.datasource.user

import com.example.core_database.entity.song.SongEntity
import com.example.core_database.entity.user.UserDownloadSongCrossRefEntity
import kotlinx.coroutines.flow.Flow

interface DownloadSongLocalDataSource {
    suspend fun insert(userDownloadSong: UserDownloadSongCrossRefEntity)
    fun isDownloadSong(userId: Int, songId: String): Flow<Boolean>
    fun getDownloadSongs(userId: Int): Flow<List<SongEntity>>
    fun getDownloadSongCount(userId: Int): Flow<Int>
    suspend fun getAllDownloadSongIds(userId: Int): List<String>
    suspend fun delete(userId: Int, songId: String)
}