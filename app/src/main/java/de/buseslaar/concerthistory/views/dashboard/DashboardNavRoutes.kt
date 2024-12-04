package de.buseslaar.concerthistory.views.dashboard

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import de.buseslaar.concerthistory.views.artistDetails.ArtistDetails
import de.buseslaar.concerthistory.views.favorites.FavoritesOverview
import de.buseslaar.concerthistory.views.favorites.FavoritesTab
import de.buseslaar.concerthistory.views.setlistDetails.SetlistDetails
import de.buseslaar.concerthistory.views.settings.SettingsOverview
import de.buseslaar.concerthistory.views.visited.VisitedOverview
import kotlinx.serialization.Serializable

@Serializable
object DashboardOverviewRoute

fun NavGraphBuilder.addDashboardNavGraph(navController: NavHostController) {
    composable<DashboardOverviewRoute> {
        DashboardView(
            onShowConcertDetails = { setlistId ->
                navController.navigate(SetlistDetails(setlistId = setlistId))
            },
            onShowMoreConcerts = { navController.navigate(VisitedOverview) },
            onShowMoreArtists = { navController.navigate(FavoritesOverview(tab = FavoritesTab.ARTISTS.ordinal)) },
            onShowArtistDetails = { artistId -> navController.navigate(ArtistDetails(artistMbId = artistId)) },
            onSettings = { navController.navigate(SettingsOverview) }
        )
    }
}