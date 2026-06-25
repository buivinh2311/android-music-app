package com.example.infrastructure.di.core

import android.content.Context
import com.example.core_network.NetworkMonitor
import com.example.core_network.api.AlbumApi
import com.example.core_network.api.ArtistApi
import com.example.core_network.api.SongApi
import com.example.infrastructure.network.NetworkMonitorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://thantrieu.com"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideSongApi(retrofit: Retrofit): SongApi {
        return retrofit.create(SongApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAlbumApi(retrofit: Retrofit): AlbumApi {
        return retrofit.create(AlbumApi::class.java)
    }

    @Provides
    @Singleton
    fun provideArtistApi(retrofit: Retrofit): ArtistApi {
        return retrofit.create(ArtistApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkMonitor(
        @ApplicationContext context: Context
    ): NetworkMonitor {
        return NetworkMonitorImpl(context)
    }
}