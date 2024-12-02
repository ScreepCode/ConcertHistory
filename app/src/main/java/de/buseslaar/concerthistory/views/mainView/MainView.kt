package de.buseslaar.concerthistory.views.mainView

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.window.core.layout.WindowWidthSizeClass
import de.buseslaar.concerthistory.navigation.BottomAppDestinations

@SuppressLint("RestrictedApi")
@Composable
fun MainView(
    navController: NavController,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {},
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val orientation = rememberUpdatedState(LocalConfiguration.current.orientation)
    val adaptiveInfo = currentWindowAdaptiveInfo()
    val customNavSuiteType = with(adaptiveInfo) {
        if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT) {
            NavigationSuiteType.NavigationBar
        } else {
            NavigationSuiteType.NavigationRail
        }
    }

    Box(
        modifier
            .fillMaxSize()
            .then(
                if (adaptiveInfo.windowSizeClass.windowWidthSizeClass != WindowWidthSizeClass.COMPACT) {
                    Modifier
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                        .windowInsetsPadding(WindowInsets.statusBars)
                } else {
                    Modifier
                        .background(MaterialTheme.colorScheme.surface)
                }
            )
    ) {
        NavigationSuiteScaffold(
            navigationSuiteColors = NavigationSuiteDefaults.colors(
                navigationBarContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                navigationRailContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            ),
            layoutType = customNavSuiteType,
            navigationSuiteItems = {
                BottomAppDestinations.entries.forEach { item ->
                    val isSelected = currentDestination?.hierarchy?.any {
                        it.hasRoute(item.route::class)
                    } == true

                    item(
                        selected = isSelected,
                        onClick = { navController.navigate(item.route) },
                        label = {
                            Text(item.label)
                        },
                        icon = {
                            Icon(
                                painter = if (isSelected) painterResource(item.activeIcon) else painterResource(
                                    item.inactiveIcon
                                ),
                                contentDescription = item.toString(),
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        modifier = Modifier,
                    )
                }
            },
            modifier = modifier
                .windowInsetsPadding(WindowInsets.displayCutout)
        ) {
            content()
        }
    }
}