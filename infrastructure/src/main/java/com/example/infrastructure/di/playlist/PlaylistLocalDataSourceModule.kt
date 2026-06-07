package com.example.infrastructure.di.playlist

import com.example.core_database.datasource.playlist.PlaylistLocalDataSource
import com.example.infrastructure.source.playlist.PlaylistLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PlaylistLocalDataSourceModule {
    @Binds
    abstract fun bindPlaylistLocalDataSource(
        impl: PlaylistLocalDataSourceImpl
    ): PlaylistLocalDataSource
}