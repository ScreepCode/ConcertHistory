package de.buseslaar.concerthistory.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import de.buseslaar.concerthistory.views.settings.SettingsViewModel

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun ConcertHistoryTheme(
    content: @Composable () -> Unit
) {
    val viewModel = viewModel<SettingsViewModel>()
    val theme by viewModel.theme.collectAsState(initial = "system")

    val colorScheme = when (theme) {
        "system" -> when {
            isSystemInDarkTheme() -> DarkColorScheme
            else -> LightColorScheme
        }

        "light" -> LightColorScheme
        "dark" -> DarkColorScheme
        else -> {
            LightColorScheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}