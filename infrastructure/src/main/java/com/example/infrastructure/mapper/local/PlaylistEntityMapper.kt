package com.example.infrastructure.mapper.local

import com.example.core_database.entity.playlist.PlaylistEntity
import com.example.core_database.entity.playlist.PlaylistWithCountEntity
import com.example.core_model.Playlist

fun Playlist.toEntity(): PlaylistEntity {
    return PlaylistEntity(
        id = this.id,
        name = this.name,
        artwork = this.artwork,
        createdAt = this.createdAt
    )
}

fun PlaylistWithCountEntity.toModel(): Playlist {
    return Playlist(
        id = this.id,
        name = this.name,
        artwork = this.artwork,
        size = this.size,
        createdAt = this.createdAt
    )
}

fun List<PlaylistWithCountEntity>.toModels(): List<Playlist> {
    return this.map { it.toModel() }
}