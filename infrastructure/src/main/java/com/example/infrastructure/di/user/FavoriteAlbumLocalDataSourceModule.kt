package com.example.infrastructure.di.user

import com.example.core_database.datasource.user.FavoriteAlbumLocalDataSource
import com.example.infrastructure.source.user.FavoriteAlbumLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FavoriteAlbumLocalDataSourceModule {
    @Binds
    abstract fun bindFavoriteAlbumLocalDataSource(
        impl: FavoriteAlbumLocalDataSourceImpl
    ): FavoriteAlbumLocalDataSource
}
