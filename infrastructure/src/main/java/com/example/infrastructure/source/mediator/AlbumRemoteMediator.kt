package com.example.infrastructure.source.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import coil.network.HttpException
import com.example.core_database.entity.album.AlbumEntity
import com.example.core_database.entity.album.AlbumRemoteKeysEntity
import com.example.core_database.entity.tracking.DBTrackingEntity
import com.example.core_network.datasource.AlbumRemoteDataSource
import com.example.core_network.dto.PagingParamRequest
import com.example.infrastructure.database.AppDatabase
import com.example.infrastructure.mapper.local.toEntities
import com.example.infrastructure.mapper.remote.toModels
import okio.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class AlbumRemoteMediator @Inject constructor(
    private val remoteDataSource: AlbumRemoteDataSource,
    private val database: AppDatabase
) : RemoteMediator<Int, AlbumEntity>() {

    private val albumDao = database.AlbumDao()
    private val albumRemoteKeysDao = database.AlbumRemoteKeysDao()
    private val dbTrackingDao = database.DBTrackingDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AlbumEntity>
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
            val albums = remoteDataSource.loadAlbumPaging(
                PagingParamRequest(
                    offset = page * limit,
                    limit = limit
                )
            ).toModels().toEntities()
            val endOfPaginationReached = albums.size < limit

            database.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    albumDao.clearAll()
                    albumRemoteKeysDao.clearAll()
                }

                val prevKey = if(page == 0) null else page - 1
                val nextKey = if(endOfPaginationReached) null else page + 1

                val keys = albums.map { album ->
                    AlbumRemoteKeysEntity(
                        id = album.id,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
                albumDao.insertAll(albums)
                albumRemoteKeysDao.insertAll(keys)
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
        state: PagingState<Int, AlbumEntity>
    ): AlbumRemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)
                ?.id?.let { songId ->
                    albumRemoteKeysDao.getAlbumRemoteKeyById(songId)
                }
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, AlbumEntity>
    ): AlbumRemoteKeysEntity? {
        return state.pages.lastOrNull { page ->
            page.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { song ->
            albumRemoteKeysDao.getAlbumRemoteKeyById(song.id)
        }
    }
}