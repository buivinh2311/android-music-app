package com.example.infrastructure.di.tracking

import com.example.core_database.datasource.tracking.DBTrackingDataSource
import com.example.infrastructure.source.tracking.DBTrackingDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DBTrackingDataSourceModule {
    @Binds
    abstract fun bindDBTrackingDataSource(
        impl: DBTrackingDataSourceImpl
    ): DBTrackingDataSource
}