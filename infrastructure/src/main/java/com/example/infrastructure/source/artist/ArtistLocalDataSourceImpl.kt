package com.example.infrastructure.source.artist

import androidx.paging.PagingSource
import com.example.core_database.dao.artist.ArtistDao
import com.example.core_database.datasource.artist.ArtistLocalDataSource
import com.example.core_database.entity.artist.ArtistEntity
import javax.inject.Inject

class ArtistLocalDataSourceImpl @Inject constructor(
    private val artistDao: ArtistDao
): ArtistLocalDataSource {
    override fun getArtistPagingSource(): PagingSource<Int, ArtistEntity> {
        return artistDao.getArtistPagingSource()
    }

    override fun getNArtistPagingSource(limit: Int): PagingSource<Int, ArtistEntity> {
        return artistDao.getNArtistPagingSource(limit)
    }

    override suspend fun getTopArtists(limit: Int): List<ArtistEntity> {
        return artistDao.getTopArtists(limit)
    }

    override suspend fun getArtistById(artistId: Int): ArtistEntity? {
        return artistDao.getArtistById(artistId)
    }

    override suspend fun getArtistByName(artistName: String): ArtistEntity? {
        return artistDao.getArtistByName(artistName)
    }

    override suspend fun insert(artist: ArtistEntity) {
        artistDao.insert(artist)
    }

    override suspend fun insertAll(artists: List<ArtistEntity>) {
        artistDao.insertAll(artists)
    }

    override suspend fun delete(artistId: Int) {
        artistDao.delete(artistId)
    }

    override suspend fun clearAll() {
        artistDao.clearAll()
    }
}