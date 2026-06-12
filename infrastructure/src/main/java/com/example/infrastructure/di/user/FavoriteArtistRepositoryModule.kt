package com.example.infrastructure.di.user

import com.example.core_domain.repository.FavoriteArtistRepository
import com.example.infrastructure.repository.FavoriteArtistRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FavoriteArtistRepositoryModule {
    @Binds
    abstract fun bindFavoriteArtistRepository(
        impl: FavoriteArtistRepositoryImpl
    ): FavoriteArtistRepository
}