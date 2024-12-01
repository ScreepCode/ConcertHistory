package de.buseslaar.concerthistory.views.favorites.artist

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.buseslaar.concerthistory.data.database.entity.Artist
import de.buseslaar.concerthistory.ui.parts.ArtistPreview

@Composable
fun ArtistFavorites(
    favoriteArtists: List<Artist>,
    onRowClick: (String) -> Unit,
    onDislikeClick: (Artist) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(favoriteArtists) { favoriteArtist ->
            with(favoriteArtist) {
                ArtistPreview(
                    name = name,
                    onRowClick = { onRowClick(favoriteArtist.mbid) },
                    isLiked = true,
                    onLikeClick = { onDislikeClick(favoriteArtist) },
                )
            }
        }
    }
}