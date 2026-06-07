package com.example.core_database.entity.song

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "song_remote_keys")
data class SongRemoteKeysEntity(
    @PrimaryKey
    @ColumnInfo(name = "song_id")
    val id: Int,

    @ColumnInfo(name = "prev_page")
    val prevKey: Int? = null,

    @ColumnInfo(name = "next_page")
    val nextKey: Int? = null
)