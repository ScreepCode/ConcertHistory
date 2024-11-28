package de.buseslaar.concerthistory.views.favorites

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import de.buseslaar.concerthistory.R
import de.buseslaar.concerthistory.data.database.entity.Setlist
import de.buseslaar.concerthistory.ui.parts.ConcertPreview
import de.buseslaar.concerthistory.ui.parts.LoadingIndicator

@Composable
fun FavoritesView() {
    val viewModel = viewModel<FavoritesViewModel>()
    val favoriteSetlists by viewModel.favoriteSetlists.collectAsState(emptyList())

    if (viewModel.isLoading) {
        LoadingIndicator()
    }

    LaunchedEffect(Unit) { viewModel.initialize() }

    Scaffold(
        topBar = {
            FavoritesAppBar()
        }
    ) { innerPadding ->
        FavoritesContent(
            favoriteSetlists = favoriteSetlists,
            onDislikeClick = { viewModel.removeConcertFromFavorites(it) },
            modifier = Modifier
                .padding(innerPadding)
        )
    }
}

@Composable
fun FavoritesContent(
    favoriteSetlists: List<Setlist>,
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
                    onRowClick = {},
                    isLiked = true,
                    onLikeClick = { onDislikeClick(favoriteSetlist) },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FavoritesAppBar() {
    TopAppBar(title = {
        Text(stringResource(R.string.setlist_favorites))
    }, actions = {

    })
}