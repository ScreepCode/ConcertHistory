package de.buseslaar.concerthistory.views.search.artist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.buseslaar.concerthistory.R
import de.buseslaar.concerthistory.data.remote.dto.ArtistDto


@Composable()
fun ArtistSearchView() {
    val viewModel = viewModel<ArtistSearchViewModel>()

    SearchView(
        onSearch = viewModel::searchArtist,
        onValueChange = viewModel::artistSearchText::set,
        placeholder = stringResource(R.string.artists),
        value = viewModel.artistSearchText,
        errorMessage = "",
        artists = viewModel.artists
    )
}


@Composable
fun SearchView(
    onSearch: () -> Unit,
    onValueChange: (String) -> Unit,
    placeholder: String,
    value: String,
    errorMessage: String,
    artists: List<ArtistDto>,
) {
    val uriHandler = LocalUriHandler.current
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = stringResource(R.string.search)
                    )
                },
                value = value,
                onValueChange = onValueChange,
                placeholder = { Text(placeholder) }, trailingIcon = {
                    Button(
                        onClick = { onSearch() },
                        content = { Text(stringResource(R.string.search)) })
                })

        }

        AnimatedVisibility(errorMessage.isNotBlank()) {
            Text(errorMessage)
        }

        LazyColumn {
            items(artists) {
                Artist(it.name, it.url, uriHandler)
            }
        }
    }

}


@Composable
fun Artist(name: String, url: String, uriHandler: UriHandler) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(name)
        Row {
            IconButton(
                content = {
                    Icon(
                        painterResource(
                            R.drawable.favorite_outline
                        ), contentDescription = ""
                    )
                },
                onClick = {
                    print("")
                },
            )
            if (url.isNotBlank()) {
                IconButton(content = {
                    Icon(Icons.Outlined.Info, contentDescription = "")
                }, onClick = {
                    uriHandler.openUri(url)
                })
            }
        }

    }

}