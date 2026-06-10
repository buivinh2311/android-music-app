package com.example.infrastructure.di.user

import com.example.core_database.datasource.user.FavoriteSongLocalDataSource
import com.example.infrastructure.source.user.FavoriteSongLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FavoriteSongLocalDataSourceModule {
    @Binds
    abstract fun bindFavoriteSongLocalDataSource(
        impl: FavoriteSongLocalDataSourceImpl
    ): FavoriteSongLocalDataSource
}