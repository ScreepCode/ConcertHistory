package de.buseslaar.concerthistory.views.search.artist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.buseslaar.concerthistory.R
import de.buseslaar.concerthistory.data.database.entity.Artist
import de.buseslaar.concerthistory.data.remote.dto.ArtistDto
import de.buseslaar.concerthistory.ui.parts.ArtistPreview
import de.buseslaar.concerthistory.ui.parts.SearchField
import kotlinx.coroutines.flow.Flow


@Composable
fun ArtistSearch(
    onSearch: () -> Unit,
    onValueChange: (String) -> Unit,
    value: String,
    errorMessage: String,
    artists: List<ArtistDto>,
    textFieldFocused: Boolean,
    onTextFieldFocusedChange: (Boolean) -> Unit = {},
    favoriteArtists: Flow<List<Artist>>,
    onShowDetails: (String) -> Unit,
    onLikeClick: (ArtistDto) -> Unit,
    onDislikeClick: (ArtistDto) -> Unit
) {

    ArtistSearchContent(
        onSearch = onSearch,
        onValueChange = onValueChange,
        placeholder = stringResource(R.string.search_artists_placeholder),
        value = value,
        errorMessage = errorMessage,
        artists = artists,
        textFieldFocused,
        onTextFieldFocusedChange = onTextFieldFocusedChange,
        favoriteArtists = favoriteArtists,
        onShowDetails = onShowDetails,
        onLikeClick = onLikeClick,
        onDislikeClick = onDislikeClick
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
    favoriteArtists: Flow<List<Artist>>,
    onShowDetails: (String) -> Unit,
    onLikeClick: (ArtistDto) -> Unit,
    onDislikeClick: (ArtistDto) -> Unit,
) {
    val favorites by favoriteArtists.collectAsState(initial = emptyList())
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
            items(artists) { artist ->
                val isLiked = favorites.any { it.mbid == artist.mbid }
                ArtistPreview(
                    artist.name,
                    onRowClick = { onShowDetails(artist.name) },
                    isLiked = isLiked,
                    onLikeClick = {
                        if (!isLiked) {
                            onLikeClick(artist)
                        } else {
                            onDislikeClick(artist)
                        }
                    }
                )
            }
        }
    }
}