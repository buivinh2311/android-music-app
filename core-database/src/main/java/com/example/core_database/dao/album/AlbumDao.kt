package com.example.core_database.dao.album

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core_database.entity.album.AlbumEntity

@Dao
interface AlbumDao {
    @Query("SELECT * FROM albums ORDER BY album_id")
    fun getAlbumPagingSource(): PagingSource<Int, AlbumEntity>

    @Query("SELECT * FROM albums ORDER BY album_id LIMIT :limit")
    fun getNAlbumPagingSource(limit: Int): PagingSource<Int, AlbumEntity>

    @Query("SELECT * FROM albums ORDER BY size DESC, album_id LIMIT :limit")
    suspend fun getTopAlbums(limit: Int): List<AlbumEntity>

    @Query("SELECT * FROM albums WHERE album_id = :albumId")
    suspend fun getAlbumById(albumId: Int): AlbumEntity?

    @Query("SELECT * FROM albums WHERE name = :albumName")
    suspend fun getAlbumByName(albumName: String): AlbumEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(album: AlbumEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(albums: List<AlbumEntity>)

    @Query("DELETE FROM albums WHERE album_id = :albumId")
    suspend fun delete(albumId: Int)

    @Query("DELETE FROM albums WHERE name NOT IN (:validNames)")
    suspend fun deleteAlbumNotIn(validNames: Set<String>)

    @Query("DELETE FROM albums")
    suspend fun clearAll()
}