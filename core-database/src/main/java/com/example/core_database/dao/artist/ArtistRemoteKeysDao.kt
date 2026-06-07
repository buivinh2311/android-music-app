package com.example.core_database.dao.artist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core_database.entity.artist.ArtistRemoteKeysEntity

@Dao
interface ArtistRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<ArtistRemoteKeysEntity>)

    @Query("SELECT * FROM artist_remote_keys WHERE artist_id = :artistId")
    suspend fun getArtistRemoteKeysById(artistId: Int): ArtistRemoteKeysEntity?

    @Query("DELETE FROM artists")
    suspend fun clearAll()
}