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
import de.buseslaar.concerthistory.ui.parts.TabSwitch
import de.buseslaar.concerthistory.ui.parts.Tabs
import de.buseslaar.concerthistory.views.search.artist.ArtistSearch
import de.buseslaar.concerthistory.views.search.artist.ArtistSearchViewModel
import de.buseslaar.concerthistory.views.search.setlist.ConcertSearch
import de.buseslaar.concerthistory.views.search.setlist.ConcertSearchViewModel

@Composable
fun SearchView() {
    val viewModel = viewModel<SearchViewModel>()
    val artistModel = viewModel<ArtistSearchViewModel>()
    val concertModel = viewModel<ConcertSearchViewModel>()
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
            artistSearchViewModel = artistModel,
            concertSearchViewModel = concertModel
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
    artistSearchViewModel: ArtistSearchViewModel,
    concertSearchViewModel: ConcertSearchViewModel
) {
    var elements = listOf<TabSwitch>(
        TabSwitch(
            label = stringResource(R.string.search_concerts_title),
            screen = {
                ConcertSearch(
                    onSearch = { concertSearchViewModel.searchConcerts() },
                    onValueChange = {
                        concertSearchViewModel.concertSearchText = it
                    },
                    value = concertSearchViewModel.concertSearchText,
                    errorMessage = concertSearchViewModel.errorMessage,
                    concerts = concertSearchViewModel.concerts,
                    textFieldFocused = concertSearchViewModel._textFieldFocused,
                    onTextFieldFocusedChange = {
                        concertSearchViewModel._textFieldFocused = it
                    }
                )
            }
        ), TabSwitch(
            label = stringResource(R.string.search_artists_title),
            screen = {
                ArtistSearch(
                    onSearch = { artistSearchViewModel.searchArtist() },
                    onValueChange = {
                        artistSearchViewModel.artistSearchText = it
                    },
                    value = artistSearchViewModel.artistSearchText,
                    errorMessage = artistSearchViewModel.errorMessage,
                    artists = artistSearchViewModel.artists,
                    textFieldFocused = artistSearchViewModel._textFieldFocused,
                    onTextFieldFocusedChange = {
                        artistSearchViewModel._textFieldFocused = it
                    }
                )
            }
        )
    )
    Column(modifier = modifier) {
        Tabs(tabs = elements, selectedTabIndex = selectedTabIndex, onTabSelected = onTabSelected)
    }

}


