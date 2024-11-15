package de.buseslaar.concerthistory.views.mainView

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
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
        if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED) {
            NavigationSuiteType.NavigationRail
        } else {
            NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(adaptiveInfo)
        }
    }

    NavigationSuiteScaffold(
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
                            imageVector = if (isSelected) item.activeIcon else item.inactiveIcon,
                            contentDescription = item.toString(),
                            modifier = Modifier.size(32.dp) // 24.dp is default size
                        )
                    },
                )
            }
        },
        modifier = modifier
    ) {
        content()
    }
}