package de.buseslaar.concerthistory.navigation

import de.buseslaar.concerthistory.R
import de.buseslaar.concerthistory.views.dashboard.DashboardRoot
import de.buseslaar.concerthistory.views.visited.VisitedRoot

enum class BottomAppDestinations(
    val route: Any,
    val label: String,
    val activeIcon: Int,
    val inactiveIcon: Int
) {
    BottomDashboard(DashboardRoot, "Dashboard", R.drawable.home_filled, R.drawable.home_outline),
    BottomVisited(VisitedRoot, "Visited", R.drawable.visited_filled, R.drawable.visited_outline),
}