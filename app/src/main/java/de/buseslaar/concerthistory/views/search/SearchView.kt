package de.buseslaar.concerthistory.views.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.buseslaar.concerthistory.R
import de.buseslaar.concerthistory.data.database.entity.Artist
import de.buseslaar.concerthistory.data.database.entity.Setlist
import de.buseslaar.concerthistory.data.remote.dto.ArtistDto
import de.buseslaar.concerthistory.data.remote.dto.SetListDto
import de.buseslaar.concerthistory.ui.parts.tabSwitch.TabElement
import de.buseslaar.concerthistory.ui.parts.tabSwitch.TabSwitch
import de.buseslaar.concerthistory.views.search.artist.ArtistSearch
import de.buseslaar.concerthistory.views.search.setlist.ConcertSearch
import kotlinx.coroutines.flow.Flow

@Composable
fun SearchView(
    initialTab: SearchTab = SearchTab.SETLISTS,
    onShowArtistDetails: (String) -> Unit,
    onShowConcertDetails: (String) -> Unit
) {
    val viewModel = viewModel<SearchViewModel>()

    LaunchedEffect(Unit) {
        viewModel.initialize(
            initialTab = initialTab
        )
    }

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
            favoriteArtists = viewModel.favoriteArtists,
            onShowArtistDetails = onShowArtistDetails,
            onLikeArtistClick = { viewModel.addArtistToFavorites(it) },
            onDislikeArtistClick = { viewModel.removeArtistFromFavorites(it) },
            favoriteSetlists = viewModel.favoriteSetlists,
            onShowConcertDetails = onShowConcertDetails,
            onLikeConcertClick = { viewModel.addConcertToFavorites(it) },
            onDislikeConcertClick = { viewModel.removeConcertFromFavorites(it) },
            isLoading = viewModel.isLoading,
            artistTextFieldContentDescription = stringResource(R.string.search_artists_placeholder),
            concertTextFieldDescription = stringResource(R.string.search_concert_placeholder)
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
    selectedTabIndex: SearchTab,
    onTabSelected: (SearchTab) -> Unit,
    searchConcerts: () -> Unit = {},
    searchArtists: () -> Unit = {},
    concertSearchText: String,
    concertTextFieldDescription: String,
    onConcertSearchTextChange: (String) -> Unit = {},
    onArtistSearchTextChange: (String) -> Unit = {},
    artistSearchText: String,
    concertErrorMessage: Int?,
    artistErrorMessage: Int?,
    concerts: List<SetListDto>,
    artists: List<ArtistDto>,
    concertTextFieldFocused: Boolean,
    artistTextFieldFocused: Boolean,
    onArtistTextFieldFocusedChange: (Boolean) -> Unit = {},
    onConcertTextFieldFocusedChange: (Boolean) -> Unit = {},
    favoriteArtists: Flow<List<Artist>>,
    favoriteSetlists: Flow<List<Setlist>>,
    onShowConcertDetails: (String) -> Unit,
    onLikeConcertClick: (SetListDto) -> Unit,
    onDislikeConcertClick: (SetListDto) -> Unit,
    onShowArtistDetails: (String) -> Unit,
    onLikeArtistClick: (ArtistDto) -> Unit,
    onDislikeArtistClick: (ArtistDto) -> Unit,
    isLoading: Boolean,
    artistTextFieldContentDescription: String
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
                    onTextFieldFocusedChange = onConcertTextFieldFocusedChange,
                    favoriteSetlists = favoriteSetlists,
                    onShowDetails = onShowConcertDetails,
                    onLikeClick = onLikeConcertClick,
                    onDislikeClick = onDislikeConcertClick,
                    isLoading = isLoading,
                    textFieldContentDescription = concertTextFieldDescription,
                )
            }
        ), TabElement(
            label = stringResource(R.string.search_artists_title),
            screen = {
                ArtistSearch(
                    isLoading = isLoading,
                    onSearch = { searchArtists() },
                    onValueChange = onArtistSearchTextChange,
                    value = artistSearchText,
                    errorMessage = artistErrorMessage,
                    artists = artists,
                    textFieldFocused = artistTextFieldFocused,
                    onTextFieldFocusedChange = onArtistTextFieldFocusedChange,
                    favoriteArtists = favoriteArtists,
                    onShowDetails = onShowArtistDetails,
                    onLikeClick = onLikeArtistClick,
                    onDislikeClick = onDislikeArtistClick,
                    textFieldContentDescription = artistTextFieldContentDescription,
                )
            }
        )
    )
    Column(modifier = modifier) {
        TabSwitch(
            tabs = elements,
            selectedTabIndex = selectedTabIndex.ordinal,
            onTabSelected = { onTabSelected(SearchTab.entries[it]) }
        )
    }

}


