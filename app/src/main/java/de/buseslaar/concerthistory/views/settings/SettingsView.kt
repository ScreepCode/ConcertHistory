package de.buseslaar.concerthistory.views.settings

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jamal.composeprefs3.ui.GroupHeader
import com.jamal.composeprefs3.ui.PrefsScreen
import com.jamal.composeprefs3.ui.prefs.DropDownPref
import com.jamal.composeprefs3.ui.prefs.TextPref
import de.buseslaar.concerthistory.BuildConfig
import de.buseslaar.concerthistory.R

// TODO: This should be a full screen dialog
@Composable
fun SettingsView(
    onBack: () -> Unit
) {
    val settingsViewModel = viewModel<SettingsViewModel>()
    val theme by settingsViewModel.theme.collectAsState(initial = "system")
    val dataStore = settingsViewModel.dataStore

    Scaffold(
        topBar = {
            SettingsAppBar(
                onBack = { onBack() }
            )
        }
    ) { innerPadding ->
        SettingsScreenContent(
            dataStore = dataStore,
            theme = theme,
            onSaveTheme = { newTheme ->
                settingsViewModel.saveTheme(newTheme)
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun SettingsScreenContent(
    dataStore: DataStore<Preferences>,
    theme: String,
    onSaveTheme: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    PrefsScreen(
        dataStore = dataStore,
        modifier = modifier
    ) {
        prefsGroup({
            GroupHeader(
                title = stringResource(R.string.settings_theme),
                color = MaterialTheme.colorScheme.onSurface,
            )
        }) {
            prefsItem {
                DropDownPref(
                    title = stringResource(R.string.settings_dark_theme_header),
                    useSelectedAsSummary = true,
                    key = "theme",
                    defaultValue = theme,
                    entries = mapOf(
                        "system" to stringResource(R.string.settings_dark_theme_system),
                        "light" to stringResource(R.string.settings_dark_theme_light),
                        "dark" to stringResource(R.string.settings_dark_theme_dark)
                    ),
                    onValueChange = { newValue ->
                        onSaveTheme(newValue)
                    },
                    dropdownBackgroundColor = MaterialTheme.colorScheme.background,
                )
            }
        }

        prefsGroup({
            GroupHeader(
                title = stringResource(R.string.settings_about_header),
                color = MaterialTheme.colorScheme.onSurface,
            )
        }) {
            prefsItem { TextPref(title = stringResource(R.string.settings_about_text)) }
        }

        prefsItem {
            TextPref(
                title = stringResource(R.string.settings_version),
                summary = BuildConfig.VERSION_NAME
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsAppBar(
    onBack: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(stringResource(R.string.settings))
        },
        navigationIcon = {
            IconButton(onClick = { onBack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.desc_back)
                )
            }
        },
    )
}