package com.example.core_database.dao.album

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core_database.entity.album.AlbumRemoteKeysEntity

@Dao
interface AlbumRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<AlbumRemoteKeysEntity>)

    @Query("SELECT * FROM album_remote_keys WHERE album_id = :albumId")
    suspend fun getAlbumRemoteKeyById(albumId: Int): AlbumRemoteKeysEntity?

    @Query("DELETE FROM album_remote_keys")
    suspend fun clearAll()
}