package com.example.core_domain.repository

import com.example.core_model.Artist

interface FavoriteArtistRepository {
    suspend fun getFavoriteArtists(): List<Artist>
    suspend fun addArtistToFavorite(artistId: Int)
    suspend fun removeArtistFromFavorite(artistId: Int)
    suspend fun isFavoriteArtist(artistId: Int): Boolean
}