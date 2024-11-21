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
import de.buseslaar.concerthistory.data.remote.dto.SetListDto

@Composable
fun ConcertPreview(
    concert: SetListDto,
    onRowClick: () -> Unit,
    isLiked: Boolean = false,
    onLikeClick: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .height(64.dp)
    ) {
        ListItem(
            headlineContent = { Text(concert.artist.name) },
            supportingContent = { Text(concert.venue.name) },
            trailingContent = {
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Row(modifier = Modifier.fillMaxHeight()) {
                        Text(concert.eventDate, modifier = Modifier.padding(8.dp))
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
                                    .clickable { }
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