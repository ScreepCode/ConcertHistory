package de.buseslaar.concerthistory.views.search


import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import de.buseslaar.concerthistory.views.setlistDetails.SetlistDetails
import kotlinx.serialization.Serializable

@Serializable
object SearchRoot

@Serializable
object Search

fun NavGraphBuilder.addSearchNavGraph(navController: NavHostController) {
    navigation<SearchRoot>(
        startDestination = Search,
    ) {
        composable<Search> {
            SearchView(
                onShowConcertDetails = { concertId ->
                    navController.navigate(SetlistDetails(setlistId = concertId))
                },

                onShowArtistDetails = { artistId ->
//                    navController.navigate(ArtistDetails(artistId = artistId))
                }
            )
        }
    }
}