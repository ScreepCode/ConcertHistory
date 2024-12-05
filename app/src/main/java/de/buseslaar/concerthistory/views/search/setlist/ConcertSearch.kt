package de.buseslaar.concerthistory.views.search.setlist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.buseslaar.concerthistory.R
import de.buseslaar.concerthistory.data.database.entity.Setlist
import de.buseslaar.concerthistory.data.remote.dto.SetListDto
import de.buseslaar.concerthistory.ui.parts.ConcertPreview
import de.buseslaar.concerthistory.ui.parts.LoadingIndicator
import de.buseslaar.concerthistory.ui.parts.SearchField
import de.buseslaar.concerthistory.ui.parts.emptyParts.NoSearchResultView
import kotlinx.coroutines.flow.Flow

@Composable
fun ConcertSearch(
    onSearch: () -> Unit,
    onValueChange: (String) -> Unit,
    value: String,
    errorMessage: Int?,
    concerts: List<SetListDto>,
    textFieldFocused: Boolean,
    onTextFieldFocusedChange: (Boolean) -> Unit = {},
    favoriteSetlists: Flow<List<Setlist>>,
    onShowDetails: (String) -> Unit,
    onLikeClick: (SetListDto) -> Unit,
    onDislikeClick: (SetListDto) -> Unit,
    isLoading: Boolean

) {

    ConcertSearchContent(
        onSearch = onSearch,
        onValueChange = onValueChange,
        placeholder = stringResource(R.string.search_concert_placeholder),
        value = value,
        errorMessage = errorMessage,
        concerts = concerts,
        textFieldFocused = textFieldFocused,
        onTextFieldFocusedChange = onTextFieldFocusedChange,
        favoriteSetlists = favoriteSetlists,
        onShowDetails = onShowDetails,
        onLikeClick = onLikeClick,
        onDislikeClick = onDislikeClick,
        isLoading = isLoading
    )
}

@Composable
fun ConcertSearchContent(
    onSearch: () -> Unit,
    onValueChange: (String) -> Unit,
    placeholder: String,
    value: String,
    errorMessage: Int?,
    concerts: List<SetListDto>,
    textFieldFocused: Boolean,
    onTextFieldFocusedChange: (Boolean) -> Unit = {},
    favoriteSetlists: Flow<List<Setlist>>,
    onShowDetails: (String) -> Unit,
    onLikeClick: (SetListDto) -> Unit,
    onDislikeClick: (SetListDto) -> Unit,
    isLoading: Boolean

) {
    val favorites by favoriteSetlists.collectAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,

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
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            if (isLoading) {
                LoadingIndicator(modifier = Modifier.padding(16.dp))
            }

            AnimatedVisibility(errorMessage != null) {
                NoSearchResultView(resourceId = errorMessage ?: R.string.search_unknown_error)
            }

            LazyColumn {
                items(concerts) { concert ->
                    val isLiked = favorites.any { it.setlistId == concert.id }
                    with(concert) {
                        ConcertPreview(
                            artistName = artist.name,
                            venueName = venue.name,
                            venueCity = venue.city.name,
                            eventDate = eventDate,
                            onRowClick = { onShowDetails(concert.id) },
                            isLiked = isLiked,
                            onLikeClick = {
                                if (!isLiked) {
                                    onLikeClick(concert)
                                } else {
                                    onDislikeClick(concert)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
