package de.buseslaar.concerthistory.views.favorites

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import de.buseslaar.concerthistory.views.artistDetails.ArtistDetails
import de.buseslaar.concerthistory.views.setlistDetails.SetlistDetails
import kotlinx.serialization.Serializable

@Serializable
object FavoritesRoot

@Serializable
data class FavoritesOverview(
    val tab: Int
)

fun NavGraphBuilder.addFavoritesNavGraph(navController: NavHostController) {
    navigation<FavoritesRoot>(
        startDestination = FavoritesOverview(tab = 0),
    ) {
        composable<FavoritesOverview> {
            val args = it.toRoute<FavoritesOverview>()
            FavoritesView(
                initialTab = FavoritesTab.entries[args.tab ?: 0],
                onShowConcertDetails = { concertId ->
                    navController.navigate(SetlistDetails(setlistId = concertId))
                },

                onShowArtistDetails = { artistId ->
                    navController.navigate(ArtistDetails(artistMbId = artistId))
                }
            )
        }
    }
}