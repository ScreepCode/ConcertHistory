package de.buseslaar.concerthistory.navigation

import de.buseslaar.concerthistory.R
import de.buseslaar.concerthistory.views.dashboard.DashboardRoot
import de.buseslaar.concerthistory.views.favorites.FavoritesRoot
import de.buseslaar.concerthistory.views.search.SearchRoot
import de.buseslaar.concerthistory.views.visited.VisitedRoot

enum class BottomAppDestinations(
    val route: Any,
    val stringResourceId: Int,
    val activeIcon: Int,
    val inactiveIcon: Int
) {
    BottomDashboard(
        DashboardRoot,
        R.string.bottomNav_dashboard, R.drawable.home_filled, R.drawable.home_outline
    ),
    BottomSearch(
        SearchRoot,
        R.string.bottomNav_search, R.drawable.search, R.drawable.search
    ),
    BottomVisited(
        VisitedRoot,
        R.string.bottomNav_visited, R.drawable.visited_filled, R.drawable.visited_outline
    ),
    BottomFavorites(
        FavoritesRoot,
        R.string.bottomNav_favorites,
        R.drawable.favorite_filled,
        R.drawable.favorite_outline
    ),
}