package de.buseslaar.concerthistory.ui.theme

import AppContextHolder
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import de.buseslaar.concerthistory.data.datastore.DataStoreServiceProvider

enum class ThemeMode {
    SYSTEM,
    LIGHT,
    DARK,
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
    val dataStoreService = DataStoreServiceProvider.getInstance()
    val theme by dataStoreService.theme.collectAsState(initial = ThemeMode.SYSTEM)

    val colorScheme = when (theme) {
        ThemeMode.SYSTEM -> when {
            isSystemInDarkTheme() -> DarkColorScheme
            else -> LightColorScheme
        }

        ThemeMode.LIGHT -> LightColorScheme
        ThemeMode.DARK -> DarkColorScheme
    }

    SetStatusBarStyle(
        isColorSchemeLight = colorScheme == LightColorScheme
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun SetStatusBarStyle(isColorSchemeLight: Boolean) {
    val view = LocalView.current
    SideEffect {
        val window = AppContextHolder.getInstance().getActiveActivity().window
        val windowInsetsController = window?.let { WindowCompat.getInsetsController(it, view) }
        if (windowInsetsController != null) {
            windowInsetsController.isAppearanceLightStatusBars = isColorSchemeLight
        }
    }
}