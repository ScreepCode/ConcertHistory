package de.buseslaar.concerthistory.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import de.buseslaar.concerthistory.views.dashboard.DashboardRoot
import de.buseslaar.concerthistory.views.dashboard.addDashboardNavGraph
import de.buseslaar.concerthistory.views.setlistDetails.addSetlistDetailsNavGraph
import de.buseslaar.concerthistory.views.settings.addSettingsNavGraph
import de.buseslaar.concerthistory.views.visited.addVisitedNavGraph

@Composable
fun ConcertHistoryNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    NavHost(
        navController = navController,
        startDestination = DashboardRoot,
        modifier = modifier
    ) {
        addDashboardNavGraph(navController)
        addSettingsNavGraph(navController)
        addVisitedNavGraph(navController)
        addSetlistDetailsNavGraph(navController)
    }
}