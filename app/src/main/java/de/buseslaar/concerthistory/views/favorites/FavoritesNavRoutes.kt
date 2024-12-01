package de.buseslaar.concerthistory.views.favorites

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import de.buseslaar.concerthistory.views.setlistDetails.SetlistDetails
import kotlinx.serialization.Serializable

@Serializable
object FavoritesRoot

@Serializable
object FavoritesOverview

fun NavGraphBuilder.addFavoritesNavGraph(navController: NavHostController) {

    composable<FavoritesOverview> {
        FavoritesView(
            onShowConcertDetails = { concertId ->
                navController.navigate(SetlistDetails(setlistId = concertId))
            },

            onShowArtistDetails = { artistId ->
//                    navController.navigate(ArtistDetails(artistId = artistId))
            }
        )
    }
}