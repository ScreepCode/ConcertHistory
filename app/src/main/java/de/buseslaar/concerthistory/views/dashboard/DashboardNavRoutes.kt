package de.buseslaar.concerthistory.views.dashboard

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import de.buseslaar.concerthistory.views.artistDetails.ArtistDetails
import de.buseslaar.concerthistory.views.setlistDetails.SetlistDetails
import de.buseslaar.concerthistory.views.settings.SettingsOverview
import de.buseslaar.concerthistory.views.visited.VisitedOverview
import kotlinx.serialization.Serializable

@Serializable
object DashboardRoot

@Serializable
object DashboardOverview


fun NavGraphBuilder.addDashboardNavGraph(navController: NavHostController) {
    navigation<DashboardRoot>(
        startDestination = DashboardOverview,
    ) {
        composable<DashboardOverview> {
            DashboardView(
                onShowConcertDetails = { setlistId ->
                    navController.navigate(SetlistDetails(setlistId = setlistId))
                },
                onShowMoreConcerts = { navController.navigate(VisitedOverview) },
                onShowMoreArtists = { /* TODO */ },
                onShowArtistDetails = { artistId -> navController.navigate(ArtistDetails(artistMbId = artistId)) },
                onSettings = { navController.navigate(SettingsOverview) }
            )
        }
    }
}