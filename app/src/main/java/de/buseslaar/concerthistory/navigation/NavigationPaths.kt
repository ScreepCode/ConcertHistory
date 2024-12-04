package de.buseslaar.concerthistory.navigation

import androidx.navigation.NavController
import de.buseslaar.concerthistory.views.dashboard.DashboardOverviewRoute

fun NavController.navigateByOverview(route: Any) = navigate(route) {
    popBackStack<DashboardOverviewRoute>(
        inclusive = (route == DashboardOverviewRoute),
        saveState = true
    )
}
