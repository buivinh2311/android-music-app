package com.example.core_database.datasource.song

import com.example.core_database.entity.song.SongListEntity

interface SongListLocalDataSource {
    suspend fun insertAll(songLists: List<SongListEntity>)
    suspend fun deleteByType(type: String)
}