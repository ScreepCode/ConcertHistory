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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jamal.composeprefs3.ui.GroupHeader
import com.jamal.composeprefs3.ui.PrefsScreen
import com.jamal.composeprefs3.ui.prefs.DropDownPref
import com.jamal.composeprefs3.ui.prefs.EditTextPref
import com.jamal.composeprefs3.ui.prefs.TextPref
import de.buseslaar.concerthistory.BuildConfig
import de.buseslaar.concerthistory.R
import de.buseslaar.concerthistory.ui.theme.ThemeMode

// TODO: This should be a full screen dialog
@Composable
fun SettingsView(
    onBack: () -> Unit
) {
    val settingsViewModel = viewModel<SettingsViewModel>()
    val theme by settingsViewModel.theme.collectAsState(initial = ThemeMode.SYSTEM)
    val setlistUsername by settingsViewModel.setlistUsername.collectAsState(initial = "")

    Scaffold(
        topBar = {
            SettingsAppBar(
                onBack = { onBack() }
            )
        }
    ) { innerPadding ->
        SettingsScreenContent(
            dataStore = settingsViewModel.dataStore,
            theme = theme,
            setlistUsername = setlistUsername,
            themeKey = settingsViewModel.themeKey,
            setlistUsernameKey = settingsViewModel.setlistUsernameKey,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SettingsScreenContent(
    dataStore: DataStore<Preferences>,
    theme: ThemeMode,
    setlistUsername: String,
    themeKey: Preferences.Key<String>,
    setlistUsernameKey: Preferences.Key<String>,
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
                    key = themeKey.toString(),
                    defaultValue = theme.toString(),
                    entries = mapOf(
                        ThemeMode.SYSTEM.toString() to stringResource(R.string.settings_dark_theme_system),
                        ThemeMode.LIGHT.toString() to stringResource(R.string.settings_dark_theme_light),
                        ThemeMode.DARK.toString() to stringResource(R.string.settings_dark_theme_dark)
                    ),
                    dropdownBackgroundColor = MaterialTheme.colorScheme.background,
                )
            }
        }

        prefsGroup({
            GroupHeader(
                title = stringResource(R.string.settings_setlist_header),
                color = MaterialTheme.colorScheme.onSurface,
            )
        }) {
            prefsItem {
                EditTextPref(
                    key = setlistUsernameKey.toString(),
                    title = stringResource(R.string.settings_setlist_userid),
                    summary = setlistUsername,
                    dialogTitle = stringResource(R.string.settings_setlist_userid),
                    dialogMessage = stringResource(R.string.settings_setlist_username_message),
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