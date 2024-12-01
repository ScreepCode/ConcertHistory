package de.buseslaar.concerthistory.views.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import de.buseslaar.concerthistory.data.database.entity.Artist
import de.buseslaar.concerthistory.data.database.entity.Setlist
import de.buseslaar.concerthistory.ui.parts.LoadingIndicator
import de.buseslaar.concerthistory.ui.parts.tabSwitch.TabElement
import de.buseslaar.concerthistory.ui.parts.tabSwitch.TabSwitch
import de.buseslaar.concerthistory.views.favorites.artist.ArtistFavorites
import de.buseslaar.concerthistory.views.favorites.setlist.SetlistFavorites

@Composable
fun FavoritesView() {
    val viewModel = viewModel<FavoritesViewModel>()
    val favoriteSetlists by viewModel.favoriteSetlists.collectAsState(emptyList())
    val favoriteArtists by viewModel.favoriteArtists.collectAsState(emptyList())

    LaunchedEffect(Unit) { viewModel.initialize() }

    Scaffold(
        topBar = {
            FavoritesAppBar()
        }
    ) { innerPadding ->
        if (viewModel.isLoading) {
            LoadingIndicator(modifier = Modifier.padding(innerPadding))
        }

        FavoritesViewContent(
            onTabSelected = { viewModel.tabIndex = it },
            selectedTabIndex = viewModel.tabIndex,
            favoriteSetlists = favoriteSetlists,
            favoriteArtists = favoriteArtists,
            onDislikeSetlistClick = {
                viewModel.removeConcertFromFavorites(it)
            },
            onDislikeArtistClick = {
                viewModel.removeArtistFromFavorites(it)
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun FavoritesViewContent(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    favoriteSetlists: List<Setlist>,
    favoriteArtists: List<Artist>,
    onDislikeSetlistClick: (Setlist) -> Unit,
    onDislikeArtistClick: (Artist) -> Unit,
    modifier: Modifier = Modifier
) {
    val elements = listOf(
        TabElement(
            label = stringResource(R.string.search_concerts_title),
            screen = {
                SetlistFavorites(
                    favoriteSetlists = favoriteSetlists,
                    onDislikeClick = { onDislikeSetlistClick(it) },
                )
            }
        ),
        TabElement(
            label = stringResource(R.string.search_artists_title),
            screen = {
                ArtistFavorites(
                    favoriteArtists = favoriteArtists,
                    onDislikeClick = { onDislikeArtistClick(it) },
                )
            }
        ),
    )

    Column(modifier = modifier) {
        TabSwitch(
            tabs = elements,
            selectedTabIndex = selectedTabIndex,
            onTabSelected = onTabSelected
        )
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