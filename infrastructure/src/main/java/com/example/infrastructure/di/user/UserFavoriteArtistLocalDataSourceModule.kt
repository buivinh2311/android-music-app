package com.example.infrastructure.di.user

import com.example.core_database.datasource.user.UserFavoriteArtistLocalDataSource
import com.example.infrastructure.source.user.UserFavoriteArtistLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserFavoriteArtistLocalDataSourceModule {
    @Binds
    abstract fun bindUserFavoriteArtistLocalDataSource(
        impl: UserFavoriteArtistLocalDataSourceImpl
    ): UserFavoriteArtistLocalDataSource
}