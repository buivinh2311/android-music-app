package com.example.core_playback.usecase

import com.example.core_domain.repository.FavoriteSongRepository
import com.example.core_domain.repository.PlaylistSongRepository
import com.example.core_domain.repository.SongRepository
import com.example.core_model.Song
import com.example.core_playback.PlaybackState
import com.example.core_playback.QueueSource
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RestorePlaybackQueueUseCase @Inject constructor(
    private val favoriteSongRepository: FavoriteSongRepository,
    private val songRepository: SongRepository,
    private val playlistSOngRepository: PlaylistSongRepository
) {
    suspend operator fun invoke(state: PlaybackState): List<Song> {
        return when(state.queueSource) {
            QueueSource.FAVORITE -> favoriteSongRepository.getFavoriteSongs().first()
            QueueSource.FOR_YOU -> songRepository.getForYouSongs(40)
            QueueSource.MOST_HEARD -> songRepository.getMostHeardSongs(40)
            QueueSource.RECOMMENDED -> songRepository.getRecommendedSongs(40)
            QueueSource.ARTIST -> songRepository.getSongsByArtistName(state.artistName.orEmpty())
            QueueSource.ALBUM -> songRepository.getSongsByAlbumName(state.albumName.orEmpty())
            QueueSource.PLAYLIST -> playlistSOngRepository.getSongsInPlaylist(state.playlistId).first()
            else -> emptyList()
        }
    }
}