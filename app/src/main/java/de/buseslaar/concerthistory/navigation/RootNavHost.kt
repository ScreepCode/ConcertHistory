package de.buseslaar.concerthistory.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import de.buseslaar.concerthistory.views.artistDetails.addArtistDetailsNavGraph
import de.buseslaar.concerthistory.views.dashboard.DashboardOverviewRoute
import de.buseslaar.concerthistory.views.dashboard.addDashboardNavGraph
import de.buseslaar.concerthistory.views.favorites.addFavoritesNavGraph
import de.buseslaar.concerthistory.views.onboarding.OnboardingRootRoute
import de.buseslaar.concerthistory.views.onboarding.addOnboardingNavGraph
import de.buseslaar.concerthistory.views.search.addSearchNavGraph
import de.buseslaar.concerthistory.views.setlistDetails.addSetlistDetailsNavGraph
import de.buseslaar.concerthistory.views.settings.addSettingsNavGraph
import de.buseslaar.concerthistory.views.visited.addVisitedNavGraph

@Composable
fun ConcertHistoryNavHost(
    navController: NavHostController,
    isOnboardingCompleted: Boolean,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    NavHost(
        navController = navController,
        startDestination = if (isOnboardingCompleted) DashboardOverviewRoute else OnboardingRootRoute,
        modifier = modifier
    ) {
        addOnboardingNavGraph(
            navController,
            onOnboardingCompleted = {
                navController.navigate(DashboardOverviewRoute) {
                    popUpTo(OnboardingRootRoute) { inclusive = true }
                }
            }
        )
        addDashboardNavGraph(navController)
        addSettingsNavGraph(navController)
        addVisitedNavGraph(navController)
        addSearchNavGraph(navController)
        addSetlistDetailsNavGraph(navController)
        addFavoritesNavGraph(navController)
        addArtistDetailsNavGraph(navController)
    }
}