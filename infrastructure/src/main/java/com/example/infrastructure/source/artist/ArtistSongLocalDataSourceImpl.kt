package com.example.infrastructure.source.artist

import com.example.core_database.dao.artist.ArtistSongCrossRefDao
import com.example.core_database.datasource.artist.ArtistSongLocalDataSource
import com.example.core_database.entity.artist.ArtistSongCrossRefEntity
import javax.inject.Inject

class ArtistSongLocalDataSourceImpl @Inject constructor(
    private val artistSongCrossRefDao: ArtistSongCrossRefDao
): ArtistSongLocalDataSource {
    override suspend fun insertAll(crossRefs: List<ArtistSongCrossRefEntity>) {
        artistSongCrossRefDao.insertAll(crossRefs)
    }

    override suspend fun getCrossRefByArtistId(artistId: Int): ArtistSongCrossRefEntity? {
        return artistSongCrossRefDao.getCrossRefByArtistId(artistId)
    }

    override suspend fun clearAll() {
        artistSongCrossRefDao.clearAll()
    }
}