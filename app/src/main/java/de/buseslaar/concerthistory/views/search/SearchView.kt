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
import de.buseslaar.concerthistory.ui.parts.Tabs
import de.buseslaar.concerthistory.utils.TabElement
import de.buseslaar.concerthistory.views.search.artist.ArtistSearchView
import de.buseslaar.concerthistory.views.search.setlist.ConcertSearchView

@Composable
fun SearchView() {
    val viewModel = viewModel<SearchViewModel>()
    Scaffold(
        topBar = {
            SearchAppBar()
        }
    ) { innerPadding ->
        SearchTabs(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 8.dp),
            onTabSelected = { viewModel.tabIndex = it },
            selectedTabIndex = viewModel.tabIndex,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar() {
    TopAppBar(title = {
        Text(stringResource(R.string.search))
    })
}

@Composable
fun SearchTabs(modifier: Modifier, selectedTabIndex: Int, onTabSelected: (Int) -> Unit) {
    var elements = listOf<TabElement>(
        TabElement(
            label = stringResource(R.string.concerts),
            screen = { ConcertSearchView() }
        ), TabElement(
            label = stringResource(R.string.artists),
            screen = { ArtistSearchView() }
        )
    )
    Column(modifier = modifier) {
        Tabs(tabs = elements, selectedTabIndex = selectedTabIndex, onTabSelected = onTabSelected)
    }

}


