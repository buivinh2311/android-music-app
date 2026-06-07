package com.example.infrastructure.di.song

import com.example.core_network.datasource.SongRemoteDataSource
import com.example.infrastructure.source.song.SongRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SongRemoteDataSourceModule {
    @Binds
    abstract fun bindSongRemoteDataSource(
        impl: SongRemoteDataSourceImpl
    ): SongRemoteDataSource
}