package com.example.infrastructure.di.core

import android.content.Context
import androidx.room.Room
import com.example.core_database.dao.album.AlbumDao
import com.example.core_database.dao.album.AlbumRemoteKeysDao
import com.example.core_database.dao.album.AlbumSongCrossRefDao
import com.example.core_database.dao.artist.ArtistDao
import com.example.core_database.dao.artist.ArtistRemoteKeysDao
import com.example.core_database.dao.artist.ArtistSongCrossRefDao
import com.example.core_database.dao.playlist.PlaylistDao
import com.example.core_database.dao.playlist.PlaylistSongCrossRefDao
import com.example.core_database.dao.song.SongDao
import com.example.core_database.dao.song.SongListDao
import com.example.core_database.dao.song.SongRemoteKeysDao
import com.example.core_database.dao.tracking.DBTrackingDao
import com.example.core_database.dao.user.UserDao
import com.example.core_database.dao.user.UserDownloadSongCrossRefDao
import com.example.core_database.dao.user.UserFavoriteAlbumCrossRefDao
import com.example.core_database.dao.user.UserFavoriteArtistCrossRefDao
import com.example.core_database.dao.user.UserFavoriteSongCrossRefDao
import com.example.core_database.dao.user.UserRecentSongCrossRefDao
import com.example.core_database.dao.user.UserSearchSongCrossRefDao
import com.example.infrastructure.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDataBase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "music.db"
            ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideAlbumDao(appDatabase: AppDatabase): AlbumDao {
        return appDatabase.AlbumDao()
    }

    @Provides
    @Singleton
    fun provideAlbumRemoteKesDao(appDatabase: AppDatabase): AlbumRemoteKeysDao {
        return appDatabase.AlbumRemoteKeysDao()
    }

    @Provides
    @Singleton
    fun provideAlbumSongCrossRefDao(appDatabase: AppDatabase): AlbumSongCrossRefDao {
        return appDatabase.AlbumSongCrossRefDao()
    }

    @Provides
    @Singleton
    fun provideArtistDao(appDatabase: AppDatabase): ArtistDao {
        return appDatabase.ArtistDao()
    }

    @Provides
    @Singleton
    fun provideArtistRemoteKeysDao(appDatabase: AppDatabase): ArtistRemoteKeysDao {
        return appDatabase.ArtistRemoteKeysDao()
    }

    @Provides
    @Singleton
    fun provideArtistSongCrossRefDao(appDatabase: AppDatabase): ArtistSongCrossRefDao {
        return appDatabase.ArtistSongCrossRefDao()
    }

    @Provides
    @Singleton
    fun providePlaylistDao(appDatabase: AppDatabase): PlaylistDao {
        return appDatabase.PlaylistDao()
    }

    @Provides
    @Singleton
    fun providePlaylistSongCrossRefDao(appDatabase: AppDatabase): PlaylistSongCrossRefDao {
        return appDatabase.PlaylistSongCrossRefDao()
    }

    @Provides
    @Singleton
    fun provideSongDao(appDatabase: AppDatabase): SongDao {
        return appDatabase.SongDao()
    }

    @Provides
    @Singleton
    fun provideSongListDao(appDatabase: AppDatabase): SongListDao {
        return appDatabase.SongListDao()
    }

    @Provides
    @Singleton
    fun provideSongRemoteKeysDao(appDatabase: AppDatabase): SongRemoteKeysDao {
        return appDatabase.SongRemoteKeysDao()
    }

    @Provides
    @Singleton
    fun provideDBTrackingDao(appDatabase: AppDatabase): DBTrackingDao {
        return appDatabase.DBTrackingDao()
    }

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.UserDao()
    }

    @Provides
    @Singleton
    fun provideUserDownloadSongCrossRefDao(appDatabase: AppDatabase): UserDownloadSongCrossRefDao {
        return appDatabase.UserDownloadSongCrossRefDao()
    }

    @Provides
    @Singleton
    fun provideUserFavoriteAlbumCrossRefDao(appDatabase: AppDatabase): UserFavoriteAlbumCrossRefDao {
        return appDatabase.UserFavoriteAlbumCrossRefDao()
    }

    @Provides
    @Singleton
    fun provideUserFavoriteArtistCrossRefDao(appDatabase: AppDatabase): UserFavoriteArtistCrossRefDao {
        return appDatabase.UserFavoriteArtistCrossRefDao()
    }

    @Provides
    @Singleton
    fun provideUserFavoriteSongCrossRefDao(appDatabase: AppDatabase): UserFavoriteSongCrossRefDao {
        return appDatabase.UserFavoriteSongCrossRefDao()
    }

    @Provides
    @Singleton
    fun provideUserRecentSongCrossRefDao(appDatabase: AppDatabase): UserRecentSongCrossRefDao {
        return appDatabase.UserRecentSongCrossRefDao()
    }

    @Provides
    @Singleton
    fun provideUserSearchSongCrossRefDao(appDatabase: AppDatabase): UserSearchSongCrossRefDao {
        return appDatabase.UserSearchSongCrossRefDao()
    }
}