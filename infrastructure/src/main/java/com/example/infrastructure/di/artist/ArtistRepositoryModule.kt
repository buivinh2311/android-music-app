package com.example.infrastructure.di.artist

import com.example.core_domain.repository.ArtistRepository
import com.example.infrastructure.repository.ArtistRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ArtistRepositoryModule {
    @Binds
    abstract fun bindArtistRepository(
        impl: ArtistRepositoryImpl
    ): ArtistRepository
}