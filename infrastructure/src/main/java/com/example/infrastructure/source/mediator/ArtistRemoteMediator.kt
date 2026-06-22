package com.example.infrastructure.source.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import coil.network.HttpException
import com.example.core_database.entity.artist.ArtistEntity
import com.example.core_database.entity.artist.ArtistRemoteKeysEntity
import com.example.core_database.entity.tracking.DBTrackingEntity
import com.example.core_network.datasource.ArtistRemoteDataSource
import com.example.core_network.dto.PagingParamRequest
import com.example.infrastructure.database.AppDatabase
import com.example.infrastructure.mapper.local.toEntities
import com.example.infrastructure.mapper.remote.toModels
import okio.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ArtistRemoteMediator @Inject constructor(
    private val remoteDataSource: ArtistRemoteDataSource,
    private val database: AppDatabase
) : RemoteMediator<Int, ArtistEntity>() {

    private val artistDao = database.ArtistDao()
    private val artistRemoteKeysDao = database.ArtistRemoteKeysDao()
    private val dbTrackingDao = database.DBTrackingDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArtistEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 0
            }

            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                nextKey
            }
        }

        return try {
            val limit = state.pages.size
            val artists = remoteDataSource.loadArtistPaging(
                PagingParamRequest(
                    offset = page * limit,
                    limit = limit
                )
            ).toModels().toEntities()
            val endOfPaginationReached = artists.size < limit

            database.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    artistDao.clearAll()
                    artistRemoteKeysDao.clearAll()
                }

                val prevKey = if(page == 0) null else page - 1
                val nextKey = if(endOfPaginationReached) null else page + 1

                val keys = artists.map { artist ->
                    ArtistRemoteKeysEntity(
                        id = artist.id,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
                artistDao.insertAll(artists)
                artistRemoteKeysDao.insertAll(keys)
                dbTrackingDao.insert(
                    DBTrackingEntity(
                        lastSongUpdated = System.currentTimeMillis()
                    )
                )
            }

            MediatorResult.Success(
                endOfPaginationReached = endOfPaginationReached
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, ArtistEntity>
    ): ArtistRemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)
                ?.id?.let { artistId ->
                    artistRemoteKeysDao.getArtistRemoteKeysById(artistId)
                }
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, ArtistEntity>
    ): ArtistRemoteKeysEntity? {
        return state.pages.lastOrNull { page ->
            page.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { artist ->
            artistRemoteKeysDao.getArtistRemoteKeysById(artist.id)
        }
    }
}