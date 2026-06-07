package com.example.core_database.datasource.user

import com.example.core_database.entity.song.SongEntity
import com.example.core_database.entity.user.UserDownloadSongCrossRefEntity

interface UserDownloadSongLocalDataSource {
    suspend fun insert(userDownloadSong: UserDownloadSongCrossRefEntity)
    suspend fun isDownloadSong(userId: Int, songId: String): Boolean
    suspend fun getDownloadSongs(userId: Int): List<SongEntity>
    suspend fun delete(userId: Int, songId: String)
}