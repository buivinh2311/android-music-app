package com.example.core_domain.repository

import com.example.core_model.Album

interface FavoriteAlbumRepository {
    suspend fun getFavoriteAlbums(): List<Album>
    suspend fun addAlbumToFavorite(albumId: Int)
    suspend fun removeAlbumFromFavorite(albumId: Int)
    suspend fun isFavoriteAlbum(albumId: Int): Boolean
}