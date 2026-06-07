package com.example.infrastructure.di.user

import com.example.core_database.datasource.user.UserFavoriteAlbumLocalDataSource
import com.example.infrastructure.source.user.UserFavoriteAlbumLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserFavoriteAlbumLocalDataSourceModule {
    @Binds
    abstract fun bindUserFavoriteAlbumLocalDataSource(
        impl: UserFavoriteAlbumLocalDataSourceImpl
    ): UserFavoriteAlbumLocalDataSource
}
