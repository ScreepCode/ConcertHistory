package de.buseslaar.concerthistory.views.visited

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.buseslaar.concerthistory.R
import de.buseslaar.concerthistory.data.database.entity.Setlist
import de.buseslaar.concerthistory.data.remote.dto.SetListDto
import de.buseslaar.concerthistory.ui.parts.ConcertPreview
import de.buseslaar.concerthistory.ui.parts.LoadingIndicator
import de.buseslaar.concerthistory.ui.parts.emptyParts.NoLastConcertsView
import de.buseslaar.concerthistory.ui.parts.emptyParts.NoUserView
import kotlinx.coroutines.flow.Flow

@Composable
fun VisitedView(
    onShowDetails: (String) -> Unit
) {
    val viewModel = viewModel<VisitedViewModel>()

    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    Scaffold(
        topBar = {
            VisitedAppBar()
        }
    ) { innerPadding ->
        if (viewModel.isLoading) {
            LoadingIndicator(modifier = Modifier.padding(innerPadding))
        }

        VisitedContent(
            isLoading = viewModel.isLoading,
            isUserNameProvided = viewModel.isUserNameProvided(),
            lastAttendedConcerts = viewModel.lastAttendedConcerts,
            favoriteSetlists = viewModel.favoriteSetlists,
            onShowDetails = onShowDetails,
            onLikeClick = { concert -> viewModel.addConcertToFavorites(concert) },
            onDislikeClick = { concert -> viewModel.removeConcertFromFavorites(concert) },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun VisitedContent(
    isLoading: Boolean,
    isUserNameProvided: Boolean,
    lastAttendedConcerts: List<SetListDto>,
    favoriteSetlists: Flow<List<Setlist>>,
    onShowDetails: (String) -> Unit,
    onLikeClick: (SetListDto) -> Unit,
    onDislikeClick: (SetListDto) -> Unit,
    modifier: Modifier = Modifier
) {
    val favorites by favoriteSetlists.collectAsState(initial = emptyList())
    AnimatedVisibility(
        !isLoading,
        enter = fadeIn(),
        exit = ExitTransition.None
    ) {
        Crossfade(
            isUserNameProvided
        ) {
            when (it) {
                true -> {
                    Crossfade(
                        lastAttendedConcerts.isNotEmpty()
                    ) { isNotEmpty ->
                        when (isNotEmpty) {
                            true -> {
                                LazyColumn(
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = modifier
                                ) {
                                    itemsIndexed(lastAttendedConcerts) { _, concert ->
                                        val isLiked = favorites.any { it.id == concert.id }

                                        with(concert) {
                                            ConcertPreview(
                                                artistName = artist.name,
                                                venueName = venue.name,
                                                venueCity = venue.city.name,
                                                eventDate = eventDate,
                                                onRowClick = { onShowDetails(concert.id) },
                                                isLiked = isLiked,
                                                onLikeClick = {
                                                    if (!isLiked) {
                                                        onLikeClick(concert)
                                                    } else {
                                                        onDislikeClick(concert)
                                                    }
                                                }
                                            )
                                        }
                                    }
                                }
                            }

                            false -> NoLastConcertsView(modifier = modifier)
                        }
                    }
                }

                false -> NoUserView(modifier = modifier)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun VisitedAppBar() {
    TopAppBar(title = {
        Text(stringResource(R.string.visited_concerts))
    })
}