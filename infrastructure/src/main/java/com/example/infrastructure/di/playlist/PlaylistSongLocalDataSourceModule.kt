package com.example.infrastructure.di.playlist

import com.example.core_database.datasource.playlist.PlaylistSongLocalDataSource
import com.example.infrastructure.source.playlist.PlaylistSongLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PlaylistSongLocalDataSourceModule {
    @Binds
    abstract fun bindPlaylistSongDataSource(
        impl: PlaylistSongLocalDataSourceImpl
    ): PlaylistSongLocalDataSource
}