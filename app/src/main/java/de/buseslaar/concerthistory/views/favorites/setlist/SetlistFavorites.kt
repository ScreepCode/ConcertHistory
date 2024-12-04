package de.buseslaar.concerthistory.views.favorites.setlist

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.buseslaar.concerthistory.R
import de.buseslaar.concerthistory.data.database.entity.Setlist
import de.buseslaar.concerthistory.ui.parts.ConcertPreview
import de.buseslaar.concerthistory.ui.parts.emptyParts.NoFavoritesView

@Composable
fun SetlistFavorites(
    favoriteSetlists: List<Setlist>,
    onRowClick: (String) -> Unit,
    onDislikeClick: (Setlist) -> Unit,
    onDeleteAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    Crossfade(favoriteSetlists.isNotEmpty()) { isNotEmpty ->
        if (isNotEmpty) {
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
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedButton(
                        onClick = {
                            onDeleteAll()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            stringResource(
                                R.string.favorites_delete_all,
                                stringResource(R.string.search_concerts_title)
                            )
                        )
                    }
                }
            }
        } else {
            NoFavoritesView(
                placeholder = stringResource(R.string.search_concerts_title),
                modifier = Modifier.background(color = MaterialTheme.colorScheme.background)
            )
        }
    }
}