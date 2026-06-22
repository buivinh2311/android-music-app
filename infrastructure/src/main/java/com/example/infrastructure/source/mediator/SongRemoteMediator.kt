package com.example.infrastructure.source.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import coil.network.HttpException
import com.example.core_database.entity.song.SongEntity
import com.example.core_database.entity.song.SongRemoteKeysEntity
import com.example.core_database.entity.tracking.DBTrackingEntity
import com.example.core_network.datasource.SongRemoteDataSource
import com.example.core_network.dto.PagingParamRequest
import com.example.infrastructure.database.AppDatabase
import com.example.infrastructure.mapper.local.toEntities
import com.example.infrastructure.mapper.remote.toModels
import okio.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class SongRemoteMediator @Inject constructor(
    private val remoteDataSource: SongRemoteDataSource,
    private val database: AppDatabase
) : RemoteMediator<Int, SongEntity>() {

    private val songDao = database.SongDao()
    private val songRemoteKeysDao = database.SongRemoteKeysDao()
    private val dbTrackingDao = database.DBTrackingDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SongEntity>
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
            val songs = remoteDataSource.loadSongPaging(
                PagingParamRequest(
                    offset = page * limit,
                    limit = limit
                )
            ).songListDto.toModels().toEntities()
            val endOfPaginationReached = songs.size < limit

            database.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    songDao.clearAll()
                    songRemoteKeysDao.clearAll()
                }

                val prevKey = if(page == 0) null else page - 1
                val nextKey = if(endOfPaginationReached) null else page + 1

                val keys = songs.map { song ->
                    SongRemoteKeysEntity(
                        id = song.id,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
                songDao.insertAll(songs)
                songRemoteKeysDao.insertAll(keys)
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
        state: PagingState<Int, SongEntity>
    ): SongRemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)
                ?.id?.let { songId ->
                    songRemoteKeysDao.getSongRemoteKeysById(songId)
                }
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, SongEntity>
    ): SongRemoteKeysEntity? {
        return state.pages.lastOrNull { page ->
            page.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { song ->
            songRemoteKeysDao.getSongRemoteKeysById(song.id)
        }
    }
}