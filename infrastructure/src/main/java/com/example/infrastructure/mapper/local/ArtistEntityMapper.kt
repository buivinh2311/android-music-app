package com.example.infrastructure.mapper.local

import com.example.core_database.entity.artist.ArtistEntity
import com.example.core_database.entity.artist.FollowedArtistEntity
import com.example.core_model.Artist

fun ArtistEntity.toModel(): Artist {
    return Artist(
        id = this.id,
        name = this.name,
        avatar = this.avatar,
        interested = this.interested
    )
}

fun Artist.toEntity(): ArtistEntity {
    return ArtistEntity(
        id = this.id,
        name = this.name,
        avatar = this.avatar,
        interested = this.interested
    )
}

fun FollowedArtistEntity.toFollowedModel(): Artist {
    return Artist(
        id = -1,
        name = this.name,
        avatar = this.avatar.orEmpty(),
        interested = this.interested
    )
}

fun List<ArtistEntity>.toModels(): List<Artist> {
    return this.map { it.toModel() }
}

fun List<FollowedArtistEntity>.toFollowedModels(): List<Artist> {
    return this.map { it.toFollowedModel() }
}

fun List<Artist>.toEntities(): List<ArtistEntity> {
    return this.map { it.toEntity() }
}