package de.buseslaar.concerthistory.views.search.setlist

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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.buseslaar.concerthistory.R
import de.buseslaar.concerthistory.data.remote.dto.SetListDto
import de.buseslaar.concerthistory.ui.parts.ConcertPreview

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
) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,

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
