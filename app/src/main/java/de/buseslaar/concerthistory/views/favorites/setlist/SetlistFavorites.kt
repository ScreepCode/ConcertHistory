package de.buseslaar.concerthistory.views.favorites.setlist

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.buseslaar.concerthistory.data.database.entity.Setlist
import de.buseslaar.concerthistory.ui.parts.ConcertPreview

@Composable
fun SetlistFavorites(
    favoriteSetlists: List<Setlist>,
    onRowClick: (String) -> Unit,
    onDislikeClick: (Setlist) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(favoriteSetlists) { favoriteSetlist ->
            with(favoriteSetlist) {
                ConcertPreview(
                    artistName = artistName,
                    venueName = venueName,
                    venueCity = venueCity,
                    eventDate = eventDate,
                    onRowClick = { onRowClick(favoriteSetlist.id) },
                    isLiked = true,
                    onLikeClick = { onDislikeClick(favoriteSetlist) },
                )
            }
        }
    }
}