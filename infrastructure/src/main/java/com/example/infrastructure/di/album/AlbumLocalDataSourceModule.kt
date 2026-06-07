package com.example.infrastructure.di.album

import com.example.core_database.datasource.album.AlbumLocalDataSource
import com.example.infrastructure.source.album.AlbumLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AlbumLocalDataSourceModule {
    @Binds
    abstract fun bindAlbumLocalDataSource(
        impl: AlbumLocalDataSourceImpl
    ): AlbumLocalDataSource
}