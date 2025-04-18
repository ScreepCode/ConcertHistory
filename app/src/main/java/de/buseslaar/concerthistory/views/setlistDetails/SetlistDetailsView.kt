package de.buseslaar.concerthistory.views.setlistDetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.buseslaar.concerthistory.R
import de.buseslaar.concerthistory.data.remote.dto.SetListDto
import de.buseslaar.concerthistory.data.remote.dto.SetsDto
import de.buseslaar.concerthistory.data.remote.dto.SongDto
import de.buseslaar.concerthistory.ui.parts.LoadingIndicator
import de.buseslaar.concerthistory.ui.parts.emptyParts.EmptySet
import de.buseslaar.concerthistory.ui.parts.emptyParts.NoConnectionView
import de.buseslaar.concerthistory.ui.parts.emptyParts.NoSetlistFound
import de.buseslaar.concerthistory.ui.parts.emptyParts.NotSetsMessage
import de.buseslaar.concerthistory.utils.formatEventDate
import java.time.format.FormatStyle
import kotlin.reflect.KFunction0

@Composable
fun SetlistDetailsView(
    selectedSetlistId: String,
    onArtistClick: (String) -> Unit,
    navigateBack: () -> Unit
) {
    val viewModel = viewModel<SetlistDetailsViewModel>()
    val menuExpanded by viewModel.menuExpanded.collectAsState()
    val selectedSetlist = viewModel.selectedSetlist

    LaunchedEffect(Unit) {
        viewModel.initialize(selectedSetlistId)
    }

    Scaffold(
        topBar = {
            SetlistDetailsAppBar(
                menuExpanded = menuExpanded,
                onMenuExpandedChange = viewModel::onMenuExpandedChange,
                setListFmUrl = selectedSetlist?.url,
                isSetlistLiked = viewModel.isLiked,
                onLikeClick = viewModel::onLikeToggle,
                onNavigateBack = navigateBack
            )
        }
    ) { innerPadding ->
        if (viewModel.isLoading) {
            LoadingIndicator(modifier = Modifier.padding(innerPadding))
        }

        if (!viewModel.isConnected()) {
            NoConnectionView()
        } else {
            SetlistDetailsContent(
                selectedSetlist = selectedSetlist,
                modifier = Modifier.padding(innerPadding),
                onArtistClick = {
                    onArtistClick(it)
                },
                isLoading = viewModel.isLoading
            )
        }
    }
}

@Composable
fun SetlistDetailsContent(
    selectedSetlist: SetListDto?,
    onArtistClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean
) {
    selectedSetlist?.let {
        LazyColumn(
            modifier = modifier
        ) {
            item {
                ConcertDetailsCard(
                    artistName = selectedSetlist.artist.name,
                    artistMbid = selectedSetlist.artist.mbid,
                    onArtistClick = onArtistClick,
                    location = "${selectedSetlist.venue.name}, ${selectedSetlist.venue.city.name}",
                    date = selectedSetlist.eventDate,
                )
            }

            if (selectedSetlist.sets.sets.isEmpty()) {
                item {
                    NotSetsMessage()
                }
            } else {
                selectedSetlist.sets.sets.let {
                    items(it) { singleSet ->
                        SetEntry(singleSet)
                    }
                }
            }
        }
    }
    AnimatedVisibility(selectedSetlist == null && !isLoading) {
        NoSetlistFound()
    }
}

@Composable
private fun ConcertDetailsCard(
    artistName: String,
    artistMbid: String,
    onArtistClick: (String) -> Unit,
    location: String,
    date: String,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier.padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .clickable {
                            onArtistClick(artistMbid)
                        }
                )
                Column {
                    Text(text = artistName)
                    Text(text = location)
                    Text(text = formatEventDate(date, FormatStyle.LONG))
                }
            }
        }
    }
}

@Composable
private fun SetEntry(
    set: SetsDto,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            set.name?.let { Text(text = it) }
            if (set.encore == 1) {
                Text(text = stringResource(R.string.setlist_details_encore))
            }
        }
        if (set.songs.isEmpty()) {
            EmptySet(modifier = Modifier.padding(8.dp))
        }
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            for (song in set.songs) {
                SongEntry(song)
            }
        }
    }
}

@Composable
private fun SongEntry(
    song: SongDto
) {
    Row {
        Text(text = song.name)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SetlistDetailsAppBar(
    menuExpanded: Boolean,
    onMenuExpandedChange: (Boolean) -> Unit,
    setListFmUrl: String? = null,
    isSetlistLiked: Boolean,
    onLikeClick: KFunction0<Unit>,
    onNavigateBack: () -> Unit,
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
            Text(stringResource(R.string.setlist_details))
        }, actions = {
            IconButton(onClick = { onLikeClick() }) {
                Icon(
                    imageVector = if (isSetlistLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (isSetlistLiked) stringResource(R.string.isLiked) else stringResource(
                        R.string.isNotLiked
                    ),
                )
            }
            MoreMenu(
                menuExpanded = menuExpanded,
                onMenuExpandedChange = { onMenuExpandedChange(it) },
                setListFmUrl = setListFmUrl
            )
        })
}

@Composable
private fun MoreMenu(
    menuExpanded: Boolean,
    onMenuExpandedChange: (Boolean) -> Unit,
    setListFmUrl: String? = null
) {
    val uriHandler = LocalUriHandler.current

    IconButton(onClick = { onMenuExpandedChange(!menuExpanded) }) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = stringResource(R.string.desc_more_menu),
        )
        DropdownMenu(expanded = menuExpanded, onDismissRequest = { onMenuExpandedChange(false) }) {
            setListFmUrl?.let {
                DropdownMenuItem(
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = stringResource(R.string.desc_info),
                            modifier = Modifier.size(24.dp),
                        )
                    },
                    text = { Text(stringResource(R.string.setlist_details_view_on_setlistfm)) },
                    onClick = {
                        uriHandler.openUri(setListFmUrl)
                        onMenuExpandedChange(false)
                    }
                )
            }
        }
    }
}