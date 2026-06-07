package com.example.core_database.entity.song

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "song_lists",
    primaryKeys = ["song_id", "list_type"],
    indices = [Index("song_id"), Index("list_type")]
)
data class SongListEntity (
    @ColumnInfo(name = "song_id")
    val id: String,

    @ColumnInfo(name = "list_type")
    val listType: String
)