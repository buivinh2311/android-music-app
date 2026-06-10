package com.example.core_database.datasource.user

import com.example.core_database.entity.artist.ArtistEntity
import com.example.core_database.entity.user.UserFavoriteArtistCrossRefEntity

interface FavoriteArtistLocalDataSource {
    suspend fun insert(userFavoriteArtist: UserFavoriteArtistCrossRefEntity)
    suspend fun isFavoriteArtist(userId: Int, artistId: Int): Boolean
    suspend fun getFavoriteArtists(userId: Int): List<ArtistEntity>
    suspend fun delete(userId: Int, artistId: Int)
}