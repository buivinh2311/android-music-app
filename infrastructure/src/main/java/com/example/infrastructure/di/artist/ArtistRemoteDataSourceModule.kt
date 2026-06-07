package com.example.infrastructure.di.artist

import com.example.core_network.datasource.ArtistRemoteDataSource
import com.example.infrastructure.source.artist.ArtistRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ArtistRemoteDataSourceModule {
    @Binds
    abstract fun bindArtistRemoteDataSource(
        impl: ArtistRemoteDataSourceImpl
    ): ArtistRemoteDataSource
}