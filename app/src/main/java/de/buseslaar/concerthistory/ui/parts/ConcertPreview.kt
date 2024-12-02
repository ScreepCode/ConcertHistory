package de.buseslaar.concerthistory.ui.parts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.buseslaar.concerthistory.R

@Composable
fun ConcertPreview(
    artistName: String,
    venueName: String,
    venueCity: String,
    eventDate: String,
    onRowClick: () -> Unit,
    isLiked: Boolean = false,
    onLikeClick: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .height(64.dp)
    ) {
        ListItem(
            headlineContent = { Text(artistName) },
            supportingContent = { Text("$venueName, $venueCity") },
            trailingContent = {
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Row(modifier = Modifier.fillMaxHeight()) {
                        Text(eventDate, modifier = Modifier.padding(8.dp))
                        onLikeClick?.let {
                            Icon(
                                painter = if (isLiked) painterResource(R.drawable.favorite_filled) else painterResource(
                                    R.drawable.favorite_outline
                                ),
                                contentDescription = if (isLiked) stringResource(R.string.isLiked) else stringResource(
                                    R.string.isNotLiked
                                ),
                                modifier = Modifier
                                    .size(32.dp)
                                    .clickable { onLikeClick.invoke() }
                                    .align(Alignment.CenterVertically)
                            )
                        }
                    }
                }
            },
            modifier = Modifier.clickable { onRowClick() }
        )
    }
}