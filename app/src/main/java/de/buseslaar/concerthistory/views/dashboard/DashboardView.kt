package de.buseslaar.concerthistory.views.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.buseslaar.concerthistory.R
import de.buseslaar.concerthistory.data.database.entity.Artist
import de.buseslaar.concerthistory.data.remote.dto.SetListDto
import de.buseslaar.concerthistory.ui.parts.ArtistPreview
import de.buseslaar.concerthistory.ui.parts.ConcertPreview
import de.buseslaar.concerthistory.ui.parts.LoadingIndicator

@Composable
fun DashboardView(
    onSettings: () -> Unit,
    onShowMoreConcerts: () -> Unit,
    onShowConcertDetails: (String) -> Unit,
    onShowMoreArtists: () -> Unit,
    onShowArtistDetails: (String) -> Unit
) {
    val viewModel = viewModel<DashboardViewModel>()
    val menuExpanded by viewModel.menuExpanded.collectAsState()
    val favoriteArtists by viewModel.favoriteArtists.collectAsState(initial = emptyList())


    LaunchedEffect(Unit) { viewModel.initialize() }

    Scaffold(
        topBar = {
            DashboardAppBar(
                menuExpanded = menuExpanded,
                onMenuExpandedChange = viewModel::onMenuExpandedChange,
                onSettings = onSettings
            )
        }
    ) { innerPadding ->
        if (viewModel.isLoading) {
            LoadingIndicator(modifier = Modifier.padding(innerPadding))
        }

        DashboardContent(
            isUserNameProvided = viewModel.isUserNameProvided(),
            lastAttendedConcerts = viewModel.lastAttendedConcerts,
            favoriteArtists = favoriteArtists,
            totalConcertsAttended = viewModel.getTotalConcertsAttended(),
            totalUniqueArtists = viewModel.getTotalUniqueArtists(),
            totalUniqueLocations = viewModel.getTotalUniqueLocations(),
            onShowMoreConcerts = onShowMoreConcerts,
            onShowConcertDetails = onShowConcertDetails,
            onShowMoreArtists = onShowMoreArtists,
            onShowArtistDetails = onShowArtistDetails,
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 8.dp)
        )
    }
}

@Composable
fun DashboardContent(
    isUserNameProvided: Boolean,
    lastAttendedConcerts: List<SetListDto>,
    favoriteArtists: List<Artist>,
    totalConcertsAttended: Int,
    totalUniqueArtists: Int,
    totalUniqueLocations: Int,
    onShowMoreConcerts: () -> Unit,
    onShowConcertDetails: (String) -> Unit,
    onShowMoreArtists: () -> Unit,
    onShowArtistDetails: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (isUserNameProvided) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier.fillMaxSize()
        ) {
            item {
                Overview(
                    totalConcertsAttended = totalConcertsAttended,
                    totalUniqueArtists = totalUniqueArtists,
                    totalUniqueLocations = totalUniqueLocations
                )
            }

            item {
                LastAttendedConcertsPreview(
                    lastAttendedConcerts = lastAttendedConcerts,
                    onClickMore = onShowMoreConcerts,
                    onClickDetails = onShowConcertDetails,
                )
            }

            item {
                FavoriteConcertsPreview(
                    favoriteArtists = favoriteArtists,
                    onClickMore = onShowMoreArtists,
                    onClickDetails = onShowArtistDetails,
                )
            }
        }
    } else {
        Text(
            stringResource(R.string.overview_no_username),
            fontSize = 21.sp,
            modifier = modifier.padding(16.dp)
        )
    }
}

@Composable
private fun Overview(
    totalConcertsAttended: Int,
    totalUniqueArtists: Int,
    totalUniqueLocations: Int
) {
    ElevatedCard {
        Text(
            stringResource(R.string.overview_overview_header),
            fontSize = 21.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            Text(stringResource(R.string.concerts_attended, totalConcertsAttended))
            Text(stringResource(R.string.different_artists, totalUniqueArtists))
            Text(stringResource(R.string.different_locations, totalUniqueLocations))
        }
    }
}

@Composable
private fun LastAttendedConcertsPreview(
    lastAttendedConcerts: List<SetListDto>,
    onClickMore: () -> Unit,
    onClickDetails: (String) -> Unit
) {
    ElevatedCard {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClickMore() }
                    .padding(16.dp)
            ) {
                Text(stringResource(R.string.overview_last_concerts_header), fontSize = 21.sp)
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = stringResource(R.string.desc_show_more),
                    modifier = Modifier.size(24.dp),
                )
            }
            lastAttendedConcerts.take(3).forEach { concert ->
                with(concert) {
                    ConcertPreview(
                        artistName = artist.name,
                        venueName = venue.name,
                        venueCity = venue.city.name,
                        eventDate = eventDate,
                        onRowClick = { onClickDetails(concert.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun FavoriteConcertsPreview(
    favoriteArtists: List<Artist>,
    onClickMore: () -> Unit,
    onClickDetails: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClickMore() }
                    .padding(16.dp)
            ) {
                Text(
                    stringResource(R.string.overview_favourite_artists_header),
                    fontSize = 21.sp,
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = stringResource(R.string.desc_show_more),
                    modifier = Modifier.size(24.dp),
                )
            }
            favoriteArtists.take(3).forEach { artist ->
                with(artist) {
                    ArtistPreview(
                        name = name,
                        onRowClick = { onClickDetails(mbid) },
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardAppBar(
    menuExpanded: Boolean,
    onMenuExpandedChange: (Boolean) -> Unit,
    onSettings: () -> Unit,
) {
    TopAppBar(title = {
        Text(stringResource(R.string.app_name))
    }, actions = {
        MoreMenu(
            menuExpanded = menuExpanded,
            onMenuExpandedChange = { onMenuExpandedChange(it) },
            onSettings = onSettings
        )
    })
}

@Composable
private fun MoreMenu(
    menuExpanded: Boolean,
    onMenuExpandedChange: (Boolean) -> Unit,
    onSettings: () -> Unit
) {
    IconButton(onClick = { onMenuExpandedChange(!menuExpanded) }) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = stringResource(R.string.desc_more_menu),
        )
        DropdownMenu(expanded = menuExpanded, onDismissRequest = { onMenuExpandedChange(false) }) {
            DropdownMenuItem(
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = stringResource(R.string.overview_more_menu_settings),
                        modifier = Modifier.size(24.dp),
                    )
                },
                text = { Text(stringResource(R.string.overview_more_menu_settings)) },
                onClick = {
                    onSettings()
                    onMenuExpandedChange(false)
                }
            )
        }
    }
}