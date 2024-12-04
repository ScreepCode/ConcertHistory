package de.buseslaar.concerthistory.views.artistDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.buseslaar.concerthistory.R
import de.buseslaar.concerthistory.data.database.entity.Setlist
import de.buseslaar.concerthistory.data.remote.dto.ArtistDto
import de.buseslaar.concerthistory.data.remote.dto.SetListDto
import de.buseslaar.concerthistory.ui.parts.ConcertPreview
import de.buseslaar.concerthistory.ui.parts.LoadingIndicator
import kotlinx.coroutines.flow.Flow

@Composable
fun ArtistDetailsView(
    selectedArtistMbId: String,
    onConcertClick: (id: String) -> Unit,
    navigateBack: () -> Unit
) {
    val viewModel = viewModel<ArtistDetailsViewModel>()

    if (viewModel.isLoading) {
        LoadingIndicator()
    }

    LaunchedEffect(Unit) {
        viewModel.initialize(selectedArtistMbId)
    }

    Scaffold(
        topBar = {
            ArtistDetailsTopAppBar(
                artistName = viewModel.selectedArtist?.name ?: "",
                isLiked = viewModel.isLiked,
                onNavigateBack = navigateBack,
                onLikeToggle = { viewModel.onLikeToggle() }
            )
        }) { innerPadding ->
        ArtistDetailsViewContent(
            artist = viewModel.selectedArtist,
            lastConcerts = viewModel.lastConcerts,
            favoriteSetlists = viewModel.favoriteSetlists,
            onShowDetails = {
                onConcertClick(it)
            },
            onLikeConcertClick = { viewModel.addConcertToFavorites(it) },
            onDislikeConcertClick = { viewModel.removeConcertFromFavorites(it) },
            modifier = Modifier.padding(innerPadding),
        )
    }

}

@Composable
fun ArtistDetailsViewContent(
    artist: ArtistDto?,
    lastConcerts: List<SetListDto>,
    favoriteSetlists: Flow<List<Setlist>>,
    onShowDetails: (String) -> Unit,
    onLikeConcertClick: (SetListDto) -> Unit,
    onDislikeConcertClick: (SetListDto) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        ArtistHeader(artist = artist)
        LastConcertsCard(
            lastConcerts = lastConcerts,
            favoriteSetlists = favoriteSetlists,
            onShowDetails = onShowDetails,
            onLikeConcertClick = onLikeConcertClick,
            onDislikeConcertClick = onDislikeConcertClick
        )
    }
}

@Composable
fun ArtistHeader(artist: ArtistDto?, modifier: Modifier = Modifier) {
    val uriHandler = LocalUriHandler.current

    ElevatedCard(
        modifier = modifier
            .padding(12.dp)
            .fillMaxWidth(),
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            IconButton(
                content = {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "",
                        modifier = Modifier.size(48.dp)
                    )
                },
                onClick = {
                    artist?.let { uriHandler.openUri(it.url) }
                }
            )
            IconButton(
                content = {
                    Icon(
                        painterResource(R.drawable.spotify),
                        contentDescription = "Spotify"
                    )
                }, onClick = {
                    uriHandler.openUri("https://open.spotify.com/search/${artist?.name}")
                })

            IconButton(
                content = {
                    Icon(
                        painterResource(R.drawable.youtube),
                        contentDescription = "YouTube"
                    )
                }, onClick = {
                    uriHandler.openUri("https://www.youtube.com/results?search_query=${artist?.name}")
                })

        }
    }
}

@Composable
fun LastConcertsCard(
    lastConcerts: List<SetListDto>,
    favoriteSetlists: Flow<List<Setlist>>,
    onShowDetails: (String) -> Unit,
    onLikeConcertClick: (SetListDto) -> Unit,
    onDislikeConcertClick: (SetListDto) -> Unit,
    modifier: Modifier = Modifier
) {
    val favorites by favoriteSetlists.collectAsState(initial = emptyList())

    ElevatedCard(modifier = modifier.padding(12.dp)) {
        Column {
            Text(
                text = stringResource(R.string.artist_details_lastConcerts),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                fontSize = 21.sp,
            )
            LazyColumn(userScrollEnabled = false) {
                items(lastConcerts.take(10)) { concert ->
                    val isLiked = favorites.any { it.id == concert.id }

                    with(concert) {
                        ConcertPreview(
                            artistName = concert.artist.name,
                            venueName = venue.name,
                            venueCity = venue.city.name,
                            eventDate = eventDate,
                            onRowClick = { onShowDetails(concert.id) },
                            isLiked = isLiked,
                            onLikeClick = {
                                if (!isLiked) {
                                    onLikeConcertClick(concert)
                                } else {
                                    onDislikeConcertClick(concert)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistDetailsTopAppBar(
    artistName: String,
    isLiked: Boolean,
    onNavigateBack: () -> Unit,
    onLikeToggle: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(
                content = {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.desc_back)
                    )
                },
                onClick = onNavigateBack
            )
        },
        title = {
            Text(artistName)
        },
        actions = {
            IconButton(onClick = onLikeToggle, content = {
                if (isLiked)
                    Icon(
                        Icons.Filled.Favorite,
                        contentDescription = stringResource(R.string.isLiked)
                    )
                else
                    Icon(
                        Icons.Filled.FavoriteBorder,
                        contentDescription = stringResource(R.string.isNotLiked)
                    )
            })
        }
    )
}