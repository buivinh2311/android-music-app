package com.example.infrastructure.di.song

import com.example.core_database.datasource.song.SongListLocalDataSource
import com.example.infrastructure.source.song.SongListLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SongListLocalDataSource {
    @Binds
    abstract fun bindSongListLocalDataSource(
        impl: SongListLocalDataSourceImpl
    ): SongListLocalDataSource
}