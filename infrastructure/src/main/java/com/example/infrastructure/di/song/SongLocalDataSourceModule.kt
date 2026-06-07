package com.example.infrastructure.di.song

import com.example.core_database.datasource.song.SongLocalDataSource
import com.example.infrastructure.source.song.SongLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SongLocalDataSourceModule {
    @Binds
    abstract fun bindSongLocalDataSource(
        impl: SongLocalDataSourceImpl
    ): SongLocalDataSource
}