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
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.buseslaar.concerthistory.R
import de.buseslaar.concerthistory.data.remote.dto.ArtistDto
import de.buseslaar.concerthistory.ui.parts.SearchField


@Composable()
fun ArtistSearch(
    onSearch: () -> Unit,
    onValueChange: (String) -> Unit,
    value: String,
    errorMessage: String,
    artists: List<ArtistDto>,
    textFieldFocused: Boolean,
    onTextFieldFocusedChange: (Boolean) -> Unit = {},
) {

    ArtistSearchContent(
        onSearch = onSearch,
        onValueChange = onValueChange,
        placeholder = stringResource(R.string.search_artists_placeholder),
        value = value,
        errorMessage = errorMessage,
        artists = artists,
        textFieldFocused,
        onTextFieldFocusedChange = onTextFieldFocusedChange
    )
}


@Composable
fun ArtistSearchContent(
    onSearch: () -> Unit,
    onValueChange: (String) -> Unit,
    placeholder: String,
    value: String,
    errorMessage: String,
    artists: List<ArtistDto>,
    textFieldFocused: Boolean,
    onTextFieldFocusedChange: (Boolean) -> Unit = {},
) {
    val uriHandler = LocalUriHandler.current
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchField(
                onPressEnter = onSearch,
                onValueChange = onValueChange,
                placeholder = placeholder,
                value = value,
                textFieldFocused = textFieldFocused,
                onTextFieldFocusedChange = onTextFieldFocusedChange
            )
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