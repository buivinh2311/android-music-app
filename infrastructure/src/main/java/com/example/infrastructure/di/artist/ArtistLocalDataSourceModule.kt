package com.example.infrastructure.di.artist

import com.example.core_database.datasource.artist.ArtistLocalDataSource
import com.example.infrastructure.source.artist.ArtistLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ArtistLocalDataSourceModule {
    @Binds
    abstract fun bindArtistLocalDataSource(
        impl: ArtistLocalDataSourceImpl
    ): ArtistLocalDataSource
}