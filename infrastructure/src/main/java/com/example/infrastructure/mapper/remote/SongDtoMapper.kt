package com.example.infrastructure.mapper.remote

import com.example.core_model.Song
import com.example.core_network.dto.SongDto

fun SongDto.toModel(): Song? {
    val id = this.id ?: return null
    return Song(
        id = id,
        title = this.title.orEmpty(),
        album = this.album.orEmpty(),
        artist = this.artist.orEmpty(),
        sourceUrl = this.sourceUrl.orEmpty(),
        artworkUrl = this.artworkUrl.orEmpty(),
        duration = this.duration ?: 0,
        favorite = this.favorite ?: 0,
        counter = this.counter ?: 0,
        replay = this.replay ?: 0
    )
}

fun Song.toDto(): SongDto {
    return SongDto(
        id = this.id,
        title = this.title,
        album = this.album,
        artist = this.artist,
        sourceUrl = this.sourceUrl,
        artworkUrl = this.artworkUrl,
        duration = duration,
        favorite = favorite,
        counter = counter,
        replay = this.replay
    )
}

fun List<Song>.toDtos(): List<SongDto> {
    return this.map { it.toDto() }
}

fun List<SongDto>.toModels(): List<Song> {
    return this.mapNotNull { it.toModel() }
}