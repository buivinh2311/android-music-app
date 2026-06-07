package com.example.infrastructure.di.artist

import com.example.core_database.datasource.artist.ArtistSongLocalDataSource
import com.example.infrastructure.source.artist.ArtistSongLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ArtistSongLocalDataSourceModule {
    @Binds
    abstract fun bindArtistSongDataSource(
        impl: ArtistSongLocalDataSourceImpl
    ): ArtistSongLocalDataSource
}