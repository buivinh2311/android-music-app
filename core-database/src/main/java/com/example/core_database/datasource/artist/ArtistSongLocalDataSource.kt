package com.example.core_database.datasource.artist

import com.example.core_database.entity.artist.ArtistSongCrossRefEntity

interface ArtistSongLocalDataSource {
    suspend fun insertAll(crossRefs: List<ArtistSongCrossRefEntity>)
    suspend fun getCrossRefByArtistId(artistId: Int): ArtistSongCrossRefEntity?
    suspend fun clearAll()
}