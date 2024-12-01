package de.buseslaar.concerthistory.ui.parts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
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
fun ArtistPreview(
    name: String,
    onRowClick: () -> Unit,
    isLiked: Boolean = false,
    onLikeClick: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .height(64.dp)
    ) {
        ListItem(
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = stringResource(R.string.desc_artistPicture),
                    modifier = Modifier.size(36.dp)
                )
            },
            headlineContent = { Text(name) },
            trailingContent = {
                Row(
                    modifier = Modifier.fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onRowClick() },
        )
    }
}