package com.example.core_database.datasource.user

import com.example.core_database.entity.artist.FollowedArtistEntity
import com.example.core_database.entity.user.UserFavoriteArtistCrossRefEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteArtistLocalDataSource {
    suspend fun insert(userFavoriteArtist: UserFavoriteArtistCrossRefEntity)
    fun isFavoriteArtist(userId: Int, artistName: String): Flow<Boolean>
    fun getFavoriteArtists(userId: Int): Flow<List<FollowedArtistEntity>>
    fun getFavoriteArtistCount(userId: Int): Flow<Int>
    suspend fun getAllFollowedArtistNames(userId: Int): List<String>
    suspend fun delete(userId: Int, artistName: String)
}