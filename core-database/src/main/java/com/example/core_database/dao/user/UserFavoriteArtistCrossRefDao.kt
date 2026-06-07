package com.example.core_database.dao.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core_database.entity.artist.ArtistEntity
import com.example.core_database.entity.song.SongEntity
import com.example.core_database.entity.user.UserFavoriteArtistCrossRefEntity

@Dao
interface UserFavoriteArtistCrossRefDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(favoriteArtist: UserFavoriteArtistCrossRefEntity)

    @Query(
        "SELECT EXISTS(" +
                "SELECT 1 FROM user_favorite_artist_cross_ref u " +
                "INNER JOIN artists a ON u.artist_id = a.artist_id " +
                "WHERE user_id = :userId AND a.artist_id = :artistId" +
                ")"
    )
    suspend fun isFavoriteArtist(userId: Int, artistId: Int): Boolean

    @Query(
        "SELECT a.* FROM user_favorite_artist_cross_ref u " +
                "INNER JOIN artists a ON u.artist_id = a.artist_id " +
                "WHERE user_id = :userId " +
                "ORDER BY u.created_at"
    )
    suspend fun getFavoriteArtists(userId: Int): List<ArtistEntity>

    @Query("DELETE FROM user_favorite_artist_cross_ref " +
            "WHERE user_id = :userId AND artist_id = :artistId")
    suspend fun delete(userId: Int, artistId: Int)
}