package com.example.core_database.dao.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core_database.entity.album.AlbumEntity
import com.example.core_database.entity.song.SongEntity
import com.example.core_database.entity.user.UserFavoriteAlbumCrossRefEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserFavoriteAlbumCrossRefDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(favoriteAlbum: UserFavoriteAlbumCrossRefEntity)

    @Query(
        "SELECT EXISTS(" +
                "SELECT 1 FROM user_favorite_album_cross_ref u " +
                "WHERE user_id = :userId AND album_name = :albumName" +
                ")"
    )
    fun isFavoriteAlbum(userId: Int, albumName: String): Flow<Boolean>

    @Query(
        "SELECT a.* FROM user_favorite_album_cross_ref u " +
                "INNER JOIN albums a ON u.album_name = a.name " +
                "WHERE user_id = :userId " +
                "ORDER BY u.created_at"
    )
    fun getFavoriteAlbums(userId: Int): Flow<List<AlbumEntity>>

    @Query(
        "SELECT COUNT(album_name) FROM user_favorite_album_cross_ref " +
                "WHERE user_id = :userId"
    )
    fun getFavoriteAlbumCount(userId: Int): Flow<Int>

    @Query(
        "DELETE FROM user_favorite_album_cross_ref " +
                "WHERE user_id = :userId AND album_name = :albumName"
    )
    suspend fun delete(userId: Int, albumName: String)
}