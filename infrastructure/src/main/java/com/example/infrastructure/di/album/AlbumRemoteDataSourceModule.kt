package com.example.infrastructure.di.album

import com.example.core_network.datasource.AlbumRemoteDataSource
import com.example.infrastructure.source.album.AlbumRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AlbumRemoteDataSourceModule {
    @Binds
    abstract fun bindAlbumRemoteDataSource(
        impl: AlbumRemoteDataSourceImpl
    ): AlbumRemoteDataSource
}