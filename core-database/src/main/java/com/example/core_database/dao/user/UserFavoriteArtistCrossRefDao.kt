package com.example.core_database.dao.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core_database.entity.artist.ArtistEntity
import com.example.core_database.entity.artist.FollowedArtistEntity
import com.example.core_database.entity.song.SongEntity
import com.example.core_database.entity.user.UserFavoriteArtistCrossRefEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserFavoriteArtistCrossRefDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteArtist: UserFavoriteArtistCrossRefEntity)

    @Query(
        "SELECT EXISTS(" +
                "SELECT 1 FROM user_favorite_artist_cross_ref " +
                "WHERE user_id = :userId AND artist_name = :artistName" +
                ")"
    )
    fun isFavoriteArtist(userId: Int, artistName: String): Flow<Boolean>

    @Query(
        "SELECT u.artist_name, a.avatar, a.interested  " +
                "FROM user_favorite_artist_cross_ref u " +
                "LEFT JOIN artists a ON u.artist_name = a.name " +
                "WHERE user_id = :userId " +
                "ORDER BY u.created_at"
    )
    fun getFavoriteArtists(userId: Int): Flow<List<FollowedArtistEntity>>

    @Query(
        "SELECT COUNT(artist_name) FROM user_favorite_artist_cross_ref " +
                "WHERE user_id = :userId"
    )
    fun getFavoriteArtistCount(userId: Int): Flow<Int>

    @Query("DELETE FROM user_favorite_artist_cross_ref " +
            "WHERE user_id = :userId AND artist_name = :artistName")
    suspend fun delete(userId: Int, artistName: String)
}