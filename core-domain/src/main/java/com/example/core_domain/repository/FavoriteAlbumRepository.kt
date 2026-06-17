package com.example.core_domain.repository

import com.example.core_model.Album
import kotlinx.coroutines.flow.Flow

interface FavoriteAlbumRepository {
    fun getFavoriteAlbums(): Flow<List<Album>>
    suspend fun addAlbumToFavorite(albumName: String)
    suspend fun removeAlbumFromFavorite(albumName: String)
    fun isFavoriteAlbum(albumName: String): Flow<Boolean>
    fun getFavoriteAlbumCount(): Flow<Int>
}