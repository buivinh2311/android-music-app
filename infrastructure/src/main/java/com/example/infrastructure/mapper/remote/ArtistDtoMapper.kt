package com.example.infrastructure.mapper.remote

import com.example.core_model.Artist
import com.example.core_network.dto.ArtistDto

fun ArtistDto.toModel(): Artist? {
    val id = this.id ?: return null
    return Artist(
        id = id,
        name = this.name.orEmpty(),
        avatar = this.avatar.orEmpty(),
        interested = this.interested ?: 0
    )
}

fun Artist.toDto(): ArtistDto {
    return ArtistDto(
        id = this.id,
        name = this.name,
        avatar = this.avatar,
        interested = this.interested
    )
}

fun List<ArtistDto>.toModels(): List<Artist> {
    return this.mapNotNull { it.toModel() }
}

fun List<Artist>.toDtos(): List<ArtistDto> {
    return this.map { it.toDto() }
}