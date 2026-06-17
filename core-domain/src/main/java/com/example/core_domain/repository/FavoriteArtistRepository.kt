package com.example.core_domain.repository

import com.example.core_model.Artist
import kotlinx.coroutines.flow.Flow

interface FavoriteArtistRepository {
    fun getFavoriteArtists(): Flow<List<Artist>>
    suspend fun addArtistToFavorite(artistName: String)
    suspend fun removeArtistFromFavorite(artistName: String)
    fun isFavoriteArtist(artistName: String): Flow<Boolean>
    fun getFavoriteArtistCount(): Flow<Int>
}