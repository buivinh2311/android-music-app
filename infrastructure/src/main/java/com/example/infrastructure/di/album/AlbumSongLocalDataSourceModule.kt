package com.example.infrastructure.di.album

import com.example.core_database.datasource.album.AlbumSongLocalDataSource
import com.example.infrastructure.source.album.AlbumSongLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AlbumSongLocalDataSourceModule {
    @Binds
    abstract fun bindAlbumSongDataSource(
        impl: AlbumSongLocalDataSourceImpl
    ): AlbumSongLocalDataSource
}