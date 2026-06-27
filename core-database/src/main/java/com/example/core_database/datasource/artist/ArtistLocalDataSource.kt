package com.example.core_database.datasource.artist

import androidx.paging.PagingSource
import com.example.core_database.entity.artist.ArtistEntity

interface ArtistLocalDataSource {
    fun getArtistPagingSource(): PagingSource<Int, ArtistEntity>
    fun getNArtistPagingSource(limit: Int): PagingSource<Int, ArtistEntity>
    suspend fun getTopArtists(limit: Int): List<ArtistEntity>
    suspend fun getArtistById(artistId: Int): ArtistEntity?
    suspend fun getArtistByName(artistName: String): ArtistEntity?
    suspend fun insert(artist: ArtistEntity)
    suspend fun insertAll(artists: List<ArtistEntity>)
    suspend fun delete(artistId: Int)
    suspend fun deleteArtistNotIn(validNames: Set<String>)
    suspend fun clearAll()
}