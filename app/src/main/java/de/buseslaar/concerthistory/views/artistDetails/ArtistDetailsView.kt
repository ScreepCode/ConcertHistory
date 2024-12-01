package de.buseslaar.concerthistory.views.artistDetails

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.buseslaar.concerthistory.R
import de.buseslaar.concerthistory.data.remote.dto.ArtistDto
import de.buseslaar.concerthistory.data.remote.dto.SetListDto
import de.buseslaar.concerthistory.ui.parts.ConcertPreview
import de.buseslaar.concerthistory.ui.parts.LoadingIndicator

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ArtistDetailsView(
    selectedArtistMbId: String,
    navigateBack: () -> Unit
) {
    var viewModel = viewModel<ArtistDetailsViewModel>()

    if (viewModel.isLoading) {
        LoadingIndicator()
    }

    LaunchedEffect(Unit) {
        viewModel.initialize(selectedArtistMbId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        content = { Icon(Icons.Filled.ArrowBack, contentDescription = "") },
                        onClick = {
                            navigateBack()
                        })
                },
                title = {
                    Text(viewModel.artist?.name ?: "")
                },
                actions = {
                    IconButton(onClick = {

                    }, content = {
                        if (viewModel.isLiked)
                            Icon(Icons.Filled.Favorite, contentDescription = "")
                        else
                            Icon(Icons.Filled.FavoriteBorder, contentDescription = "")

                    })
                }

            )
        }) { innerPadding ->
        ArtistDetailsViewContent(
            artist = viewModel.artist,
            lastConcerts = viewModel.lastConcerts,
            modifier = Modifier.padding(innerPadding),
        )
    }

}

@Composable
fun ArtistDetailsViewContent(
    artist: ArtistDto?,
    lastConcerts: List<SetListDto>,
    modifier: Modifier = Modifier,
    onLikeClick: () -> Unit = {}
) {
    val uriHandler = LocalUriHandler.current
    Column(modifier = modifier) {
        ElevatedCard(
            modifier = Modifier
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
                Text(artist?.disambiguation ?: "")

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
        ElevatedCard(modifier = Modifier.padding(12.dp)) {
            Column {
                Text(
                    text = stringResource(R.string.artist_details_lastConcerts),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    fontSize = 21.sp,
                )
                LazyColumn(userScrollEnabled = false) {
                    items(lastConcerts.take(10)) { item ->
                        ConcertPreview(concert = item, onRowClick = {
                            uriHandler.openUri(item.url)
                        })
                    }
                }
            }

        }
    }
}