package com.example.infrastructure.mapper.remote

import com.example.core_model.Album
import com.example.core_network.dto.AlbumDto

fun AlbumDto.toModel(): Album? {
    val id = this.id ?: return null
    return Album(
        id = id,
        name = this.name.orEmpty(),
        artworkUrl = this.artworkUrl.orEmpty(),
        size = this.size ?: 0
    )
}

fun Album.toDto(): AlbumDto {
    return AlbumDto(
        id = this.id,
        name = this.name,
        artworkUrl = this.artworkUrl,
        size = this.size
    )
}

fun List<AlbumDto>.toModels(): List<Album> {
    return this.mapNotNull { it.toModel() }
}

fun List<Album>.toDtos(): List<AlbumDto> {
    return this.map { it.toDto() }
}