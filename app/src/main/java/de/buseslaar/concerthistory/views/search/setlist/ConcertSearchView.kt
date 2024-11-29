package de.buseslaar.concerthistory.views.search.setlist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.buseslaar.concerthistory.R
import de.buseslaar.concerthistory.data.remote.dto.SetListDto
import de.buseslaar.concerthistory.ui.parts.ConcertPreview
import de.buseslaar.concerthistory.ui.parts.SearchField

@Composable
fun ConcertSearchView() {
    val viewModel = viewModel<ConcertSearchViewModel>()
    SearchView(
        onSearch = viewModel::searchConcerts,
        onValueChange = viewModel::concertSearchText::set,
        placeholder = stringResource(R.string.concerts),
        value = viewModel.concertSearchText,
        errorMessage = viewModel.errorMessage,
        concerts = viewModel.concerts,
        textFieldFocused = viewModel._textFieldFocused,
        onTextFieldFocusedChange = {
            viewModel._textFieldFocused = it
        },
    )
}

@Composable
fun SearchView(
    onSearch: () -> Unit,
    onValueChange: (String) -> Unit,
    placeholder: String,
    value: String,
    errorMessage: String,
    concerts: List<SetListDto>,
    textFieldFocused: Boolean,
    onTextFieldFocusedChange: (Boolean) -> Unit = {},
) {

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            SearchField(
                onEnterPress = onSearch,
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
            AnimatedVisibility(errorMessage.isNotBlank()) {
                Text(errorMessage)
            }

            LazyColumn {
                items(concerts) {
                    ConcertPreview(concert = it, onRowClick = {}, isLiked = false)
                }
            }
        }
    }
}
