package de.buseslaar.concerthistory.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import de.buseslaar.concerthistory.views.settings.SettingsViewModel

enum class ThemeMode(
    val value: String,
) {
    SYSTEM("system"),
    LIGHT("light"),
    DARK("dark"),
}

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
    val theme = viewModel.theme

    val colorScheme = when (theme) {
        ThemeMode.SYSTEM -> when {
            isSystemInDarkTheme() -> DarkColorScheme
            else -> LightColorScheme
        }

        ThemeMode.LIGHT -> LightColorScheme
        ThemeMode.DARK -> DarkColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}