package com.example.core_database.datasource.user

import com.example.core_database.entity.album.AlbumEntity
import com.example.core_database.entity.user.UserFavoriteAlbumCrossRefEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteAlbumLocalDataSource {
    suspend fun insert(userFavoriteAlbum: UserFavoriteAlbumCrossRefEntity)
    fun isFavoriteAlbum(userId: Int, albumName: String): Flow<Boolean>
    suspend fun getFavoriteAlbums(userId: Int): List<AlbumEntity>
    suspend fun delete(userId: Int, albumName: String)
}