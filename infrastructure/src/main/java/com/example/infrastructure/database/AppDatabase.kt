package com.example.infrastructure.database

import androidx.room.Database
import androidx.room.RoomDatabase
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
import com.example.core_database.entity.album.AlbumEntity
import com.example.core_database.entity.album.AlbumRemoteKeysEntity
import com.example.core_database.entity.album.AlbumSongCrossRefEntity
import com.example.core_database.entity.artist.ArtistEntity
import com.example.core_database.entity.artist.ArtistRemoteKeysEntity
import com.example.core_database.entity.artist.ArtistSongCrossRefEntity
import com.example.core_database.entity.user.UserFavoriteArtistCrossRefEntity
import com.example.core_database.entity.playlist.PlaylistEntity
import com.example.core_database.entity.playlist.PlaylistSongCrossRefEntity
import com.example.core_database.entity.user.UserFavoriteSongCrossRefEntity
import com.example.core_database.entity.song.SongEntity
import com.example.core_database.entity.song.SongListEntity
import com.example.core_database.entity.song.SongRemoteKeysEntity
import com.example.core_database.entity.tracking.DBTrackingEntity
import com.example.core_database.entity.user.UserDownloadSongCrossRefEntity
import com.example.core_database.entity.user.UserEntity
import com.example.core_database.entity.user.UserFavoriteAlbumCrossRefEntity
import com.example.core_database.entity.user.UserRecentSongCrossRefEntity
import com.example.core_database.entity.user.UserSearchSongCrossRefEntity

@Database(
    entities = [
        AlbumEntity::class,
        AlbumRemoteKeysEntity::class,
        AlbumSongCrossRefEntity::class,
        ArtistEntity::class,
        ArtistRemoteKeysEntity::class,
        ArtistSongCrossRefEntity::class,
        PlaylistEntity::class,
        PlaylistSongCrossRefEntity::class,
        SongEntity::class,
        SongListEntity::class,
        SongRemoteKeysEntity::class,
        DBTrackingEntity::class,
        UserDownloadSongCrossRefEntity::class,
        UserEntity::class,
        UserFavoriteAlbumCrossRefEntity::class,
        UserFavoriteArtistCrossRefEntity::class,
        UserFavoriteSongCrossRefEntity::class,
        UserRecentSongCrossRefEntity::class,
        UserSearchSongCrossRefEntity::class
    ],
    version = 7
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun AlbumDao(): AlbumDao
    abstract fun AlbumRemoteKeysDao(): AlbumRemoteKeysDao
    abstract fun AlbumSongCrossRefDao(): AlbumSongCrossRefDao
    abstract fun ArtistDao(): ArtistDao
    abstract fun ArtistRemoteKeysDao(): ArtistRemoteKeysDao
    abstract fun ArtistSongCrossRefDao(): ArtistSongCrossRefDao
    abstract fun PlaylistDao(): PlaylistDao
    abstract fun PlaylistSongCrossRefDao(): PlaylistSongCrossRefDao
    abstract fun SongDao(): SongDao
    abstract fun SongListDao(): SongListDao
    abstract fun SongRemoteKeysDao(): SongRemoteKeysDao
    abstract fun DBTrackingDao(): DBTrackingDao
    abstract fun UserDao(): UserDao
    abstract fun UserDownloadSongCrossRefDao(): UserDownloadSongCrossRefDao
    abstract fun UserFavoriteAlbumCrossRefDao(): UserFavoriteAlbumCrossRefDao
    abstract fun UserFavoriteArtistCrossRefDao(): UserFavoriteArtistCrossRefDao
    abstract fun UserFavoriteSongCrossRefDao(): UserFavoriteSongCrossRefDao
    abstract fun UserRecentSongCrossRefDao(): UserRecentSongCrossRefDao
    abstract fun UserSearchSongCrossRefDao(): UserSearchSongCrossRefDao
}