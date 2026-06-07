package com.example.infrastructure.di.user

import com.example.core_database.datasource.user.UserFavoriteSongLocalDataSource
import com.example.infrastructure.source.user.UserFavoriteSongLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserFavoriteSongLocalDataSourceModule {
    @Binds
    abstract fun bindUserFavoriteSongLocalDataSource(
        impl: UserFavoriteSongLocalDataSourceImpl
    ): UserFavoriteSongLocalDataSource
}