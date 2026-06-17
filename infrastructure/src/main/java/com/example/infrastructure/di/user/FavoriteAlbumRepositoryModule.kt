package com.example.infrastructure.di.user

import com.example.core_domain.repository.FavoriteAlbumRepository
import com.example.infrastructure.repository.FavoriteAlbumRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FavoriteAlbumRepositoryModule {
    @Binds
    abstract fun bindFavoriteAlbumRepository(
        impl: FavoriteAlbumRepositoryImpl
    ): FavoriteAlbumRepository
}