package com.example.infrastructure.di.user

import com.example.core_domain.manager.UserManager
import com.example.infrastructure.repository.DefaultUserManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DefaultUserManagerModule {
    @Binds
    abstract fun bindDefaultUserManager(
        impl: DefaultUserManager
    ): UserManager
}