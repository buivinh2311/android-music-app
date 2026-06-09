package com.example.infrastructure.di.user

import com.example.core_domain.repository.FavoriteSongRepository
import com.example.infrastructure.repository.FavoriteSongRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FavoriteSongRepositoryModule {
    @Binds
    abstract fun bindFavoriteSongRepository(
        impl: FavoriteSongRepositoryImpl
    ): FavoriteSongRepository
}