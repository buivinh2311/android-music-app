package com.example.infrastructure.mapper.local

import com.example.core_database.entity.song.SongEntity
import com.example.core_model.DisplaySong
import com.example.core_model.Song

fun SongEntity.toModel(): Song {
    return Song(
        id = this.id,
        title = this.title,
        album = this.album,
        artist = this.artist,
        sourceUrl = this.sourceUrl,
        artworkUrl = this.artworkUrl,
        duration = this.duration,
        favorite = this.favorite,
        counter = this.counter,
        replay = this.replay
    )
}

fun SongEntity.toDisplayModel(): DisplaySong {
    return DisplaySong(
        id = this.id,
        title = this.title,
        album = this.album,
        artist = this.artist,
        artworkUrl = this.artworkUrl
    )
}

fun Song.toEntity(): SongEntity {
    return SongEntity(
        id = this.id,
        title = this.title,
        album = this.album,
        artist = this.artist,
        sourceUrl = this.sourceUrl,
        artworkUrl = this.artworkUrl,
        duration = this.duration,
        favorite = this.favorite,
        counter = this.counter,
        replay = this.replay
    )
}

fun List<SongEntity>.toModels(): List<Song> {
    return this.map { it.toModel() }
}

fun List<SongEntity>.toDisplayModels(): List<DisplaySong> {
    return this.map { it.toDisplayModel() }
}

fun List<Song>.toEntities(): List<SongEntity> {
    return this.map { it.toEntity() }
}