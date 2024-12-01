package de.buseslaar.concerthistory.views.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.buseslaar.concerthistory.R
import de.buseslaar.concerthistory.data.remote.dto.ArtistDto
import de.buseslaar.concerthistory.data.remote.dto.SetListDto
import de.buseslaar.concerthistory.ui.parts.TabElement
import de.buseslaar.concerthistory.ui.parts.TabSwitch
import de.buseslaar.concerthistory.views.search.artist.ArtistSearch
import de.buseslaar.concerthistory.views.search.setlist.ConcertSearch

@Composable
fun SearchView(
    onDetailsClick: (String) -> Unit,
) {
    val viewModel = viewModel<SearchViewModel>()
    Scaffold(
        topBar = {
            SearchAppBar()
        }
    ) { innerPadding ->
        SearchViewContent(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 8.dp),
            onTabSelected = { viewModel.tabIndex = it },
            selectedTabIndex = viewModel.tabIndex,
            searchConcerts = { viewModel.searchConcerts() },
            searchArtists = { viewModel.searchArtist() },
            concertSearchText = viewModel.concertSearchText,
            onConcertSearchTextChange = {
                viewModel.concertSearchText = it
            },
            onArtistSearchTextChange = {
                viewModel.artistSearchText = it
            },
            artistSearchText = viewModel.artistSearchText,
            concertErrorMessage = viewModel.concertErrorMessage,
            artistErrorMessage = viewModel.artistErrorMessage,
            concerts = viewModel.concerts,
            artists = viewModel.artists,
            concertTextFieldFocused = viewModel.concertTextFieldFocused,
            artistTextFieldFocused = viewModel.artistTextFieldFocused,
            onArtistTextFieldFocusedChange = {
                viewModel.artistTextFieldFocused = it
            },
            onConcertTextFieldFocusedChange = {
                viewModel.concertTextFieldFocused = it
            },
            onDetailsClick = onDetailsClick
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar() {
    TopAppBar(title = {
        Text(stringResource(R.string.search_title))
    })
}

@Composable
fun SearchViewContent(
    modifier: Modifier,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    searchConcerts: () -> Unit = {},
    searchArtists: () -> Unit = {},
    concertSearchText: String,
    onConcertSearchTextChange: (String) -> Unit = {},
    onArtistSearchTextChange: (String) -> Unit = {},
    artistSearchText: String,
    concertErrorMessage: String,
    artistErrorMessage: String,
    concerts: List<SetListDto>,
    artists: List<ArtistDto>,
    concertTextFieldFocused: Boolean,
    artistTextFieldFocused: Boolean,
    onArtistTextFieldFocusedChange: (Boolean) -> Unit = {},
    onConcertTextFieldFocusedChange: (Boolean) -> Unit = {},
    onDetailsClick: (String) -> Unit,
) {
    var elements = listOf<TabElement>(
        TabElement(
            label = stringResource(R.string.search_concerts_title),
            screen = {
                ConcertSearch(
                    onSearch = { searchConcerts() },
                    onValueChange = onConcertSearchTextChange,
                    value = concertSearchText,
                    errorMessage = concertErrorMessage,
                    concerts = concerts,
                    textFieldFocused = concertTextFieldFocused,
                    onTextFieldFocusedChange = onConcertTextFieldFocusedChange
                )
            }
        ), TabElement(
            label = stringResource(R.string.search_artists_title),
            screen = {
                ArtistSearch(
                    onSearch = { searchArtists() },
                    onValueChange = onArtistSearchTextChange,
                    value = artistSearchText,
                    errorMessage = artistErrorMessage,
                    artists = artists,
                    textFieldFocused = artistTextFieldFocused,
                    onTextFieldFocusedChange = onArtistTextFieldFocusedChange,
                    onDetailsClick,
                )
            }
        )
    )
    Column(modifier = modifier) {
        TabSwitch(
            tabs = elements,
            selectedTabIndex = selectedTabIndex,
            onTabSelected = onTabSelected
        )
    }

}


