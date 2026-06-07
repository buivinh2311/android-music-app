package com.example.core_database.dao.artist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core_database.entity.artist.ArtistSongCrossRefEntity

@Dao
interface ArtistSongCrossRefDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(crossRefs: List<ArtistSongCrossRefEntity>)

    @Query("SELECT * FROM artist_song_cross_ref WHERE artist_id = :artistId")
    suspend fun getCrossRefByArtistId(artistId: Int): ArtistSongCrossRefEntity?

    @Query("DELETE FROM artist_song_cross_ref")
    suspend fun clearAll()
}