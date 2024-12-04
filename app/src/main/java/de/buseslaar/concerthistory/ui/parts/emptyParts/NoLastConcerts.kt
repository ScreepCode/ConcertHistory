package de.buseslaar.concerthistory.ui.parts.emptyParts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.buseslaar.concerthistory.R

@Composable
fun NoLastConcertsView(
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painterResource(R.drawable.visited_outline),
                contentDescription = stringResource(R.string.visited_concerts),
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 8.dp)
            )
            Text(
                stringResource(R.string.no_last_concert_title),
                style = MaterialTheme.typography.headlineMedium
            )
        }
        Spacer(modifier = Modifier.size(32.dp))
        Text(
            stringResource(R.string.no_last_concert_message),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.size(32.dp))

        OutlinedButton(
            onClick = { uriHandler.openUri("https://www.setlist.fm/advanced") },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(stringResource(R.string.no_last_concert_action))
        }
    }
}

@Composable
fun NoLastConcertsMessage(
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Text(
            stringResource(R.string.no_last_concert_title),
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            stringResource(R.string.no_last_concert_message),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}