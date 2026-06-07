package com.example.core_database.datasource.user

import com.example.core_database.entity.song.SongEntity
import com.example.core_database.entity.user.UserFavoriteSongCrossRefEntity

interface UserFavoriteSongLocalDataSource {
    suspend fun insert(userFavoriteSong: UserFavoriteSongCrossRefEntity)
    suspend fun isFavoriteSong(userId: Int, songId: String): Boolean
    suspend fun getFavoriteSongs(userId: Int): List<SongEntity>
    suspend fun delete(userId: Int, songId: String)
}