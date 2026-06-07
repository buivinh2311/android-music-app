package com.example.infrastructure.mapper.local

import com.example.core_database.entity.playlist.PlaylistEntity
import com.example.core_model.Playlist

fun PlaylistEntity.toModel(): Playlist {
    return Playlist(
        id = this.id,
        name = this.name,
        artwork = this.artwork,
        createdAt = this.createdAt
    )
}

fun Playlist.toEntity(): PlaylistEntity {
    return PlaylistEntity(
        id = this.id,
        name = this.name,
        artwork = this.artwork,
        createdAt = this.createdAt
    )
}

fun List<PlaylistEntity>.toModels(): List<Playlist> {
    return this.map { it.toModel() }
}