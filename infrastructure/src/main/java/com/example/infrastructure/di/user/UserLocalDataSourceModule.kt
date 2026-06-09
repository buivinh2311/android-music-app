package com.example.infrastructure.di.user

import com.example.core_database.datasource.user.UserLocalDataSource
import com.example.infrastructure.source.user.UserLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserLocalDataSourceModule {
    @Binds
    abstract fun bindUserLocalDataSource(
        impl: UserLocalDataSourceImpl
    ): UserLocalDataSource
}