package de.buseslaar.concerthistory.views.favorites.artist

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import de.buseslaar.concerthistory.R
import de.buseslaar.concerthistory.data.database.entity.Artist
import de.buseslaar.concerthistory.ui.parts.ArtistPreview
import de.buseslaar.concerthistory.ui.parts.emptyParts.NoFavoritesView

@Composable
fun ArtistFavorites(
    favoriteArtists: List<Artist>,
    onRowClick: (String) -> Unit,
    onDislikeClick: (Artist) -> Unit,
    modifier: Modifier = Modifier
) {
    Crossfade(favoriteArtists.isNotEmpty()) { isNotEmpty ->
        if (isNotEmpty) {
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
        } else {
            NoFavoritesView(
                placeholder = stringResource(R.string.search_artists_title),
                modifier = Modifier.background(color = MaterialTheme.colorScheme.background)
            )
        }
    }
}