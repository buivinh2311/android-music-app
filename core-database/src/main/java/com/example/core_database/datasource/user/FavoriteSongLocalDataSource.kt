package com.example.core_database.datasource.user

import com.example.core_database.entity.song.SongEntity
import com.example.core_database.entity.user.UserFavoriteSongCrossRefEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteSongLocalDataSource {
    suspend fun insert(userFavoriteSong: UserFavoriteSongCrossRefEntity)
    fun isFavoriteSong(userId: Int, songId: String): Flow<Boolean>
    fun getFavoriteSongs(userId: Int): Flow<List<SongEntity>>
    suspend fun delete(userId: Int, songId: String)
}