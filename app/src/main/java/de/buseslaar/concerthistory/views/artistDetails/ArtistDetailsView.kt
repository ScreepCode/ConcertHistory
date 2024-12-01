package de.buseslaar.concerthistory.views.artistDetails

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.buseslaar.concerthistory.data.remote.dto.ArtistDto
import de.buseslaar.concerthistory.data.remote.dto.SetListDto
import de.buseslaar.concerthistory.ui.parts.ConcertPreview
import de.buseslaar.concerthistory.ui.parts.LoadingIndicator

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ArtistDetailsView(
    selectedArtistMbId: String
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
                title = {
                    Text(viewModel.artist?.name ?: "")
                }
            )
        }) { innerPadding ->
        ArtistDetailsViewContent(
            artist = viewModel.artist,
            lastConcerts = viewModel.lastConcerts,
            modifier = Modifier.padding(innerPadding)
        )
    }

}

@Composable
fun ArtistDetailsViewContent(
    artist: ArtistDto?,
    lastConcerts: List<SetListDto>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Card(modifier = Modifier.padding(12.dp)) {
            Row {
                Icon(Icons.Default.Person, contentDescription = "", modifier = Modifier.size(48.dp))
            }
        }
        Card {
            LazyColumn {
                items(lastConcerts) { item ->
                    ConcertPreview(concert = item, onRowClick = {})
                }
            }
        }
    }
}