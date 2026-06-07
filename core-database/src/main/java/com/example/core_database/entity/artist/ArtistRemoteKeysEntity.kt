package com.example.core_database.entity.artist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artist_remote_keys")
data class ArtistRemoteKeysEntity(
    @PrimaryKey
    @ColumnInfo(name = "artist_id")
    val id: Int,

    @ColumnInfo(name = "prev_page")
    val prevKey: Int? = null,

    @ColumnInfo(name = "next_page")
    val nextKey: Int? = null
)