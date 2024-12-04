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
fun NoUserView(
    modifier: Modifier = Modifier
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
                painterResource(R.drawable.settings_alert),
                contentDescription = stringResource(R.string.settings),
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 8.dp)
            )
            Text(
                stringResource(R.string.no_username_title),
                style = MaterialTheme.typography.headlineMedium
            )
        }
        Spacer(modifier = Modifier.size(32.dp))
        Text(
            stringResource(R.string.no_username_request),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.size(16.dp))

        Text(
            stringResource(R.string.no_username_guide),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.size(32.dp))

        OutlinedButton(
            onClick = { uriHandler.openUri("https://www.setlist.fm/signup") },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(stringResource(R.string.no_username_create_account))
        }
    }

}