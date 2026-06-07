package com.example.core_database.dao.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core_database.entity.album.AlbumEntity
import com.example.core_database.entity.song.SongEntity
import com.example.core_database.entity.user.UserFavoriteAlbumCrossRefEntity

@Dao
interface UserFavoriteAlbumCrossRefDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(favoriteAlbum: UserFavoriteAlbumCrossRefEntity)

    @Query(
        "SELECT EXISTS(" +
                "SELECT 1 FROM user_favorite_album_cross_ref u " +
                "INNER JOIN albums a ON u.album_id = a.album_id " +
                "WHERE user_id = :userId AND a.album_id = :albumId" +
                ")"
    )
    suspend fun isFavoriteAlbum(userId: Int, albumId: Int): Boolean

    @Query(
        "SELECT a.* FROM user_favorite_album_cross_ref u " +
                "INNER JOIN albums a ON u.album_id = a.album_id " +
                "WHERE user_id = :userId " +
                "ORDER BY u.created_at"
    )
    suspend fun getFavoriteAlbums(userId: Int): List<AlbumEntity>

    @Query(
        "DELETE FROM user_favorite_album_cross_ref " +
                "WHERE user_id = :userId AND album_id = :albumId"
    )
    suspend fun delete(userId: Int, albumId: Int)
}