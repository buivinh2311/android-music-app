package com.example.core_database.dao.artist

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.core_database.entity.artist.ArtistEntity

@Dao
interface ArtistDao {
    @Query("SELECT * FROM artists ORDER BY artist_id")
    fun getArtistPagingSource(): PagingSource<Int, ArtistEntity>

    @Query("SELECT * FROM artists ORDER BY artist_id LIMIT :limit")
    fun getNArtistPagingSource(limit: Int): PagingSource<Int, ArtistEntity>

    @Query("SELECT * FROM artists ORDER BY interested DESC, artist_id LIMIT :limit")
    suspend fun getTopArtists(limit: Int): List<ArtistEntity>

    @Query("SELECT * FROM artists WHERE artist_id = :artistId")
    suspend fun getArtistById(artistId: Int): ArtistEntity?

    @Query("SELECT * FROM artists WHERE name = :artistName")
    suspend fun getArtistByName(artistName: String): ArtistEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(artist: ArtistEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(artists: List<ArtistEntity>)

    @Query("DELETE FROM artists WHERE artist_id = :artistId")
    suspend fun delete(artistId: Int)

    @Query("DELETE FROM artists WHERE name NOT IN (:validNames)")
    suspend fun deleteArtistNotIn(validNames: Set<String>)

    @Query("DELETE FROM artists")
    suspend fun clearAll()
}