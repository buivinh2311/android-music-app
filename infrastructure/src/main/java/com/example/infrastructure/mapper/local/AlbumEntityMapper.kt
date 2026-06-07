package com.example.infrastructure.mapper.local

import com.example.core_database.entity.album.AlbumEntity
import com.example.core_model.Album

fun AlbumEntity.toModel(): Album {
    return Album(
        id = this.id,
        name = this.name,
        artworkUrl= this.artworkUrl,
        size = this.size
    )
}

fun Album.toEntity(): AlbumEntity {
    return AlbumEntity(
        id = this.id,
        name = this.name,
        artworkUrl = this.artworkUrl,
        size = this.size
    )
}

fun List<AlbumEntity>.toModels(): List<Album> {
    return this.map { it.toModel() }
}

fun List<Album>.toEntities(): List<AlbumEntity> {
    return this.map { it.toEntity() }
}