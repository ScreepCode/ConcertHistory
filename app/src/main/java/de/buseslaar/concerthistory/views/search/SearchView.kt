package de.buseslaar.concerthistory.views.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.buseslaar.concerthistory.R
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
        SearchContent(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 8.dp),
            viewModel.tabIndex,
            onTabSelected = { viewModel.tabIndex = it },
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
fun SearchContent(
    modifier: Modifier,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
) {
    Column(modifier) {
        SearchTabBar(selectedTabIndex = selectedTabIndex, onTabSelected)

        when (selectedTabIndex) {
            0 -> ConcertSearchView()
            1 -> ArtistSearchView()
        }
    }
}

@Composable
fun SearchTabBar(selectedTabIndex: Int, onTabSelected: (Int) -> Unit) {
    TabRow(selectedTabIndex) {
        Tab(
            selected = selectedTabIndex == 0,
            text = { Text(stringResource(R.string.concerts)) },
            onClick = { onTabSelected(0) })
        Tab(
            selected = selectedTabIndex == 1,
            text = { Text(stringResource(R.string.artists)) },
            onClick = { onTabSelected(1) })
    }
}


