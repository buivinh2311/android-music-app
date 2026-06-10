package com.example.core_database.datasource.user

import com.example.core_database.entity.album.AlbumEntity
import com.example.core_database.entity.user.UserFavoriteAlbumCrossRefEntity

interface FavoriteAlbumLocalDataSource {
    suspend fun insert(userFavoriteAlbum: UserFavoriteAlbumCrossRefEntity)
    suspend fun isFavoriteAlbum(userId: Int, albumId: Int): Boolean
    suspend fun getFavoriteAlbums(userId: Int): List<AlbumEntity>
    suspend fun delete(userId: Int, albumId: Int)
}