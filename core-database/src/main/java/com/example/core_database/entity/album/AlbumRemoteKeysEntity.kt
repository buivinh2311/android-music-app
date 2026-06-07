package com.example.core_database.entity.album

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("album_remote_keys")
data class AlbumRemoteKeysEntity (
    @PrimaryKey
    @ColumnInfo(name = "album_id")
    val id: Int,

    @ColumnInfo(name = "prev_page")
    val prevKey: Int? = null,

    @ColumnInfo(name = "next_page")
    val nextKey: Int? = null
)