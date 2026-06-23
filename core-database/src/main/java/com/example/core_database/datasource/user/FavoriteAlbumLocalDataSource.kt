package com.example.core_database.datasource.user

import com.example.core_database.entity.album.AlbumEntity
import com.example.core_database.entity.user.UserFavoriteAlbumCrossRefEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteAlbumLocalDataSource {
    suspend fun insert(userFavoriteAlbum: UserFavoriteAlbumCrossRefEntity)
    fun isFavoriteAlbum(userId: Int, albumName: String): Flow<Boolean>
    fun getFavoriteAlbums(userId: Int): Flow<List<AlbumEntity>>
    fun getFavoriteAlbumCount(userId: Int): Flow<Int>
    suspend fun getAllFavoriteAlbumNames(userId: Int): List<String>
    suspend fun delete(userId: Int, albumName: String)
}